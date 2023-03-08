package com.demo.auth.payload.request;

import lombok.Data;
import java.util.Set;

import jakarta.validation.constraints.*;

@Data
public class SignupRequest {
    
    @NotBlank
    @Size(min = 3,max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @Pattern(regexp="[0-9]{10}")
    private String phoneNumber;

    @Size(max = 1000)
    private String address;

    @NotNull
    private Double salary;    

    private Set<String> role;
}
