package com.security.security.filters;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private String username;
    private List<String> roles;
}