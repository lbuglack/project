package com.topGame.service;

public interface SecurityService {

    String findLoggedInUser(); // find logged in user
    void autoLogin(String username, String password); // authorisation
}
