package com.topGame.service;

import com.topGame.entity.User;
import com.topGame.entity.VerificationToken;

public interface UserService {

    User save(User user);
    User findByUsername(String username);
    void createVerificationToken(User user, String token);
    VerificationToken getVerificationToken(String VerificationToken);

}
