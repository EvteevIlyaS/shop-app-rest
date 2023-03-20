package com.ilyaevteev.shopapp.dto.auth;

import lombok.Data;

@Data
public class RegistrationDto {
    private String username;
    private String email;
    private String password;
    private Long balance;
}
