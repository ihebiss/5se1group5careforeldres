package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.User;

public class UserForbiddenException extends RuntimeException {
    private User user;

    public UserForbiddenException(String message, User user) {
        super(message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
