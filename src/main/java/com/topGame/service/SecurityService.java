package com.topGame.service;

public interface SecurityService {

    void autoLogin(String username, String password);

    String validateToken(long id, String token);
}
