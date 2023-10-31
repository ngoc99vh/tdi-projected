package com.example.tdiframework.rest;

import com.example.tdiframework.config.JwtTokenUtil;
import com.example.tdiframework.dto.ResponseObject;
import com.example.tdiframework.dto.request.ChangePasswordRequest;
import com.example.tdiframework.dto.request.ForgotPasswordRequest;
import com.example.tdiframework.dto.request.JwtRequest;
import com.example.tdiframework.dto.request.UserRequest;
import com.example.tdiframework.service.JwtUserDetailsService;
import com.example.tdiframework.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
public class UserResource {
    private final UserService service;

    public UserResource(UserService service, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService jwtUserDetailsService) {
        this.service = service;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject<?>> createAuthenticationToken(@Valid @RequestBody JwtRequest request) throws Exception {
        return ResponseEntity.ok(service.authentication(request));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseObject<?>> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(service.registerAccount(request));
    }

    @GetMapping(value = "/get-info-user")
    public ResponseEntity<?> getInfoUser(HttpServletRequest request) {
        return ResponseEntity.ok(service.getInfoUser(request));
    }


    @PostMapping(value = "/change-password")
    public ResponseEntity<ResponseObject<String>> changePassword(HttpServletRequest httpServletRequest,@Valid  @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(service.changePassword(httpServletRequest, request));
    }

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<ResponseObject<String>> getInfoUser(@Valid @RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(service.forgotPassword(request));
    }

}
