package com.buysellcars.dto;

import com.buysellcars.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private  String jwt;
    private Long userId;
    private UserRole userRole;
}
