package com.example.tdiframework.service.impl;
import com.example.tdiframework.config.JwtTokenUtil;
import com.example.tdiframework.domain.User;
import com.example.tdiframework.dto.ResponseObject;
import com.example.tdiframework.dto.request.ChangePasswordRequest;
import com.example.tdiframework.dto.request.ForgotPasswordRequest;
import com.example.tdiframework.dto.request.JwtRequest;
import com.example.tdiframework.dto.request.UserRequest;
import com.example.tdiframework.dto.response.GetInfoUserResponse;
import com.example.tdiframework.dto.response.JwtResponse;
import com.example.tdiframework.exception.BaseException;
import com.example.tdiframework.reponsitory.UserRepository;
import com.example.tdiframework.service.JwtUserDetailsService;
import com.example.tdiframework.service.UserService;
import com.example.tdiframework.utils.CommonUtils;
import com.example.tdiframework.utils.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.example.tdiframework.utils.Constants.AUTHORIZATION;
import static com.example.tdiframework.utils.Constants.PREFIX_TOKEN;

@Service
public class UserServiceImlp implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImlp.class);

    private final UserRepository userReponsitory;

    private final PasswordEncoder passwordEncoder;

    private final CommonUtils commonUtils;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    private final JwtUserDetailsService jwtUserDetailsService;

    public UserServiceImlp(UserRepository userReponsitory, PasswordEncoder passwordEncoder, CommonUtils commonUtils, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, JwtUserDetailsService jwtUserDetailsService) {
        this.userReponsitory = userReponsitory;
        this.passwordEncoder = passwordEncoder;
        this.commonUtils = commonUtils;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    /**
     * Lấy thông tin user bằng sdt
     *
     * @param phone
     * @return the entity
     */
    @Override
    public User getUserByPhone(String phone) {
        User user = userReponsitory.findUserByPhone(phone);
        if (user != null && !ObjectUtils.isEmpty(user)) {
            return user;
        } else {
            log.error("User not found!!!");
            throw new UsernameNotFoundException("User not found!!!");
        }
    }

    /**
     * Đăng ký tài khoản
     * @param request
     * @return String
     */
    @Override
    public ResponseObject<?> registerAccount(UserRequest request) {
        try {
            // Kiểm tra số điện thoại đã tồn tại trong DB
            User userInDB = userReponsitory.findUserByPhone(request.getPhone());
            if (!ObjectUtils.isEmpty(userInDB)) {
                return ResponseObject.error(BaseException.PrefixErrorCode.AUTH, ErrorMessage.REGISTERED_PHONE_NUMBER, null);
            }
            User user = new User();
            user.setPhone(request.getPhone());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setCustomerId(generateCustomerId());
            user.setCustomerName(StringUtils.isEmpty(request.getCustomerName()) ?
                    commonUtils.generateUUIDString() : request.getCustomerName());
            user.setCreatedDate(LocalDateTime.now());
            user.setStatus("ACTIVE");
            userReponsitory.save(user);
            // Login luôn sau khi đăng ký
            User infoUser = getUserByPhone(request.getPhone());
            authenticate(request.getPhone(), request.getPassword());
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getPhone());
            final String token = jwtTokenUtil.generateToken(userDetails);
            final String expiredToken = String.valueOf(jwtTokenUtil.getExpirationDateFromToken(token).getTime());
            return ResponseObject.success("Đăng ký tài khoản thành công", "Register account successful", new JwtResponse(infoUser.getCustomerName(), infoUser.getPhone(), token, expiredToken));
        } catch (Exception e) {
            log.error("===> An error has occurred: {}", e.getMessage());
            return ResponseObject.error(BaseException.PrefixErrorCode.AUTH, ErrorMessage.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Đăng nhập
     * @param request
     * @return the entity
     * @throws Exception
     */
    @Override
    public ResponseObject<?> authentication(JwtRequest request) throws Exception {
        try {
            User infoUser = getUserByPhone(request.getPhone());
            // Kiểm tra tính đúng của mật khẩu hiện tại
            if (!passwordEncoder.matches(request.getPassword(), infoUser.getPassword())) {
                return ResponseObject.error(BaseException.PrefixErrorCode.AUTH, ErrorMessage.PHONE_PASSWORD_NOT_FOUND, null);
            }
            authenticate(request.getPhone(), request.getPassword());
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getPhone());
            final String token = jwtTokenUtil.generateToken(userDetails);
//            final String expiredToken = getExpiredTokenToHourAndMinutes(token);
            final String expiredToken = String.valueOf(jwtTokenUtil.getExpirationDateFromToken(token).getTime());
            return ResponseObject.success("Đăng nhập thành công", "Login successful", new JwtResponse(infoUser.getCustomerName(), infoUser.getPhone(), token, expiredToken));
        } catch (Exception e) {
            log.error("===> An error has occurred: {}", e.getMessage());
            return ResponseObject.error(BaseException.PrefixErrorCode.AUTH, ErrorMessage.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    /**
     * Truy vấn thông tin của account admin với token đăng nhập
     * @param request
     * @return the entity
     * @throws Exception
     */
    @Override
    public User getInfoAccAdmin(HttpServletRequest request) {
        String authorHeader = request.getHeader(AUTHORIZATION);
        String token = null;
        if (authorHeader != null && authorHeader.startsWith(PREFIX_TOKEN)) {
            token = authorHeader.substring(PREFIX_TOKEN.length());
        }
        User user = userReponsitory.findUserByPhone(jwtTokenUtil.getUsernameFromToken(token));
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("User not found!!!");
        } else {
//            GetInfoUserResponse response = new GetInfoUserResponse();
//            response.setCustomerId(user.getCustomerId());
//            response.setCustomerName(user.getCustomerName());
//            response.setPhone(user.getPhone());
            return user;
        }
    }

    /**
     * Truy vấn thông tin của account user với token đăng nhập
     * @param request
     * @return
     */
    @Override
    public GetInfoUserResponse getInfoUser(HttpServletRequest request) {
        String authorHeader = request.getHeader(AUTHORIZATION);
        String token = null;
        if (authorHeader != null && authorHeader.startsWith(PREFIX_TOKEN)) {
            token = authorHeader.substring(PREFIX_TOKEN.length());
        }
        User user = userReponsitory.findUserByPhone(jwtTokenUtil.getUsernameFromToken(token));
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("User not found!!!");
        } else {
            GetInfoUserResponse response = new GetInfoUserResponse();
            response.setCustomerId(user.getCustomerId());
            response.setCustomerName(user.getCustomerName());
            response.setPhone(user.getPhone());
            return response;
        }
    }

    /**
     * generateCustomerId - Tự động generate customerId
     * @return String
     */
    private String generateCustomerId() {
        int lastCustomerId = Integer.parseInt(userReponsitory.findMaxCustomerId() != null ? userReponsitory.findMaxCustomerId() : "100000");
        return String.valueOf(lastCustomerId + 1);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLE", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    /**
     * Đổi mật khẩu
     * @param changePasswordRequest
     * @return String
     */
    @Override
    public ResponseObject<String> changePassword(HttpServletRequest request, ChangePasswordRequest changePasswordRequest) {
        try {
            // Kiểm tra tính đúng của mật khẩu hiện tại
            User userInDB = userReponsitory.findByCustomerId(getInfoUser(request).getCustomerId());
            if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), userInDB.getPassword())) {
                return ResponseObject.error(BaseException.PrefixErrorCode.AUTH, ErrorMessage.CURRENT_PASSWORD_ERROR, null);
            }
            // Check trùng mật khẩu mới với mật khẩu cũ
            if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getCurrentPassword())) {
                return ResponseObject.error(BaseException.PrefixErrorCode.AUTH, ErrorMessage.DUPLICATE_PASSWORD, null);
            }
            // Check newPassword với renewPassword có khớp nhau không ?
            if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getRenewPassword())) {
                return ResponseObject.error(BaseException.PrefixErrorCode.AUTH, ErrorMessage.RE_ENTER_PASSWORD_ERROR, null);
            }
            // Cập nhật password
            User user = new User();
            user.setCustomerId(userInDB.getCustomerId());
            user.setCustomerName(userInDB.getCustomerName());
            user.setPhone(userInDB.getPhone());
            user.setStatus(userInDB.getStatus());
            user.setCreatedDate(userInDB.getCreatedDate());
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            user.setUpdateDate(LocalDateTime.now());
            userReponsitory.save(user);
            return ResponseObject.success("Đổi mật khẩu thành công", "Change password successful","SUCCESS");
        } catch (Exception e) {
            log.error("===> An error has occurred: {}", e.getMessage());
            return ResponseObject.error(BaseException.PrefixErrorCode.AUTH, ErrorMessage.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    /**
     * Quên mật khẩu
     * @param forgotPasswordRequest
     * @return String
     */
    @Override
    public ResponseObject<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        try {
            // Kiểm tra user có tồn tại
            User userInDB = userReponsitory.findUserByPhone(forgotPasswordRequest.getPhone());
            if (ObjectUtils.isEmpty(userInDB)) {
                return ResponseObject.error(BaseException.PrefixErrorCode.AUTH, ErrorMessage.NO_DATA, "Số điện thoại không tồn tại");
            }
            // Cập nhật mật khẩu user
            User user = new User();
            user.setCustomerId(userInDB.getCustomerId());
            user.setCustomerName(userInDB.getCustomerName());
            user.setPhone(userInDB.getPhone());
            user.setStatus(userInDB.getStatus());
            user.setCreatedDate(userInDB.getCreatedDate());
            user.setPassword(passwordEncoder.encode(forgotPasswordRequest.getPassword()));
            user.setUpdateDate(LocalDateTime.now());
            userReponsitory.save(user);
            return ResponseObject.success("Lấy lại mật khẩu thành công", "Password recovery successful","SUCCESS");
        } catch (Exception e) {
            log.error("===> An error has occurred: {}", e.getMessage());
            return ResponseObject.error(BaseException.PrefixErrorCode.AUTH, ErrorMessage.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

//    private String getExpiredTokenToHourAndMinutes(String token) {
//        final Date expiredToken = jwtTokenUtil.getExpirationDateFromToken(token);
//        long startDate = new Date().getTime();
//        long endDate = expiredToken.getTime();
//        long timeRemaining = endDate - startDate;
////        LocalDateTime localDateTime = LocalDateTime.ofInstant(timeRemaining);
//        return null;
//    }
}