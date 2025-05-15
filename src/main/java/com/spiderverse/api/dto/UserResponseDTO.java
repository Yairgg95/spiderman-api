package com.spiderverse.api.dto;

public class UserResponseDTO {
    private String username;

    public UserResponseDTO(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
}
