package com.example.tdiframework.dto.request;

import com.example.tdiframework.validator.PhoneConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ForgotPasswordRequest {

    @NotBlank(message = "Số điện thoại không được để trống")
    @PhoneConstraint
    private String phone;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}
