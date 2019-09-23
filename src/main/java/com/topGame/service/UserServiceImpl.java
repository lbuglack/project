package com.topGame.service;

import com.topGame.entity.Role;
import com.topGame.entity.User;
import com.topGame.entity.Token;
import com.topGame.repository.RoleRepository;
import com.topGame.repository.UserRepository;
import com.topGame.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    private MessageSource messages;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository, MessageSource messages) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.messages = messages;

    }

    @Override
    public User save(User newUser) {

        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        setRolesForUser(user);

        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Token getToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }


    @Override
    public void saveToken(User user, String token) {

        Token myToken = new Token(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public SimpleMailMessage constructResetTokenMessage(String contextPath, Locale locale, String token, User user) {

        String url = createUrl(contextPath, token, user);
        String message = messages.getMessage("message.resetPassword", null, locale);

        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    @Override
    public SimpleMailMessage constructEmail(String subject, String body, User user) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());

        return email;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void changeUserPassword(User user, String password) {

        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
    }

    private String createUrl(String contextPath, String token, User user) {

        String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
        return url;
    }

    private void setRolesForUser(User user) {

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findOne(1L));
        user.setRoles(roles);
    }
}
