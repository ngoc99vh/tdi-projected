package com.example.tdiframework.service;

import com.example.tdiframework.domain.User;
import com.example.tdiframework.dto.ResponseObject;
import com.example.tdiframework.dto.request.ChangePasswordRequest;
import com.example.tdiframework.dto.request.ForgotPasswordRequest;
import com.example.tdiframework.dto.request.JwtRequest;
import com.example.tdiframework.dto.request.UserRequest;
import com.example.tdiframework.dto.response.GetInfoUserResponse;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User getUserByPhone(String phone);

    ResponseObject<?> registerAccount(UserRequest request);

    GetInfoUserResponse getInfoUser(HttpServletRequest request);

    ResponseObject<?> authentication(JwtRequest request) throws Exception;

    User getInfoAccAdmin(HttpServletRequest request);

    ResponseObject<String> changePassword(HttpServletRequest request, ChangePasswordRequest changePasswordRequest);

    ResponseObject<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

}
