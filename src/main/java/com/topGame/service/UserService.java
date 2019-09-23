package com.topGame.service;

import com.topGame.entity.User;
import com.topGame.entity.Token;
import org.springframework.mail.SimpleMailMessage;

import java.util.Locale;

public interface UserService {

    User save(User user);

    User findByUsername(String username);

    void saveToken(User user, String token);

    Token getToken(String VerificationToken);

    SimpleMailMessage constructResetTokenMessage(String contextPath, Locale locale, String token, User user);

    SimpleMailMessage constructEmail(String subject, String body, User user);

    User findUserByEmail(String email);

    void changeUserPassword(User user, String password);
}
