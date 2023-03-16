package com.demo.auth.payload.response;

import java.util.List;

import com.demo.auth.entity.app.MemberType;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private String phoneNumber;
    private Double salary;
    private String address;
    private String refCode;
    private MemberType memberType;
    private List<String> roles;

    public JwtResponse(
        String accessToken, 
        Long id, 
        String username, 
        String phoneNumber, 
        Double salary, 
        String address, 
        String refCode, 
        MemberType memberType,
        String refreshToken, 
        List<String> roles) 
    {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.address = address;
        this.refCode = refCode;
        this.memberType = memberType;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }
}
