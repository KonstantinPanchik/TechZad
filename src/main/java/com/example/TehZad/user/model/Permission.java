package com.example.TehZad.user.model;

public enum Permission {
    READ("user:read"),
    CHANGE("user:change");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
