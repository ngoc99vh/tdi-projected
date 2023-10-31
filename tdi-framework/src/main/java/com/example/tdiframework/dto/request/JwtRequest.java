package com.example.tdiframework.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = -3218217744632934267L;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
