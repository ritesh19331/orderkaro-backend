package com.orderkaro.config;

public final class CurrentUser {

    private CurrentUser() {}

    public static UserContext get() {
        UserContext context = UserContextHolder.get();

        if (context == null) {
            throw new IllegalStateException("UserContext not initialized");
        }

        return context;
    }

    public static String userId() {
        return get().getUserId();
    }

    public static String email() {
        return get().getEmail();
    }

    public static boolean hasRole(String role) {
        return get().getRoles().contains(role);
    }
}

