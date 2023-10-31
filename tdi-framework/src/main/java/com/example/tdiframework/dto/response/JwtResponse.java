package com.example.tdiframework.dto.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 7564750209741580927L;

    private String customerName;
    private String phone;
    private String jwtToken;

    private String expiredToken;

    public JwtResponse(String customerName, String phone, String jwtToken, String expiredToken) {
        this.customerName = customerName;
        this.phone = phone;
        this.jwtToken = jwtToken;
        this.expiredToken = expiredToken;
    }
}
