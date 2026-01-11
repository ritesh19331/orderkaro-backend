package com.orderkaro.config;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserContext {

    private String userId;
    private String username;
    private String email;
    private Set<String> roles;
}
