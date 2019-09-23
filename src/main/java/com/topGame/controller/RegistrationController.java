package com.topGame.controller;

import com.topGame.entity.User;
import com.topGame.entity.Token;
import com.topGame.service.SecurityService;
import com.topGame.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


public class RegistrationController {

    private UserServiceImpl userService;
    private MessageSource messages;
    private SecurityService securityService;


    @Autowired
    public RegistrationController(UserServiceImpl userService, MessageSource messages,
                                  SecurityService securityService) {

        this.userService = userService;
        this.messages = messages;
        this.securityService = securityService;
    }

    @GetMapping(value = "/registration/confirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("id") Long id,
                                                      @RequestParam("token") String token) {

        String result = securityService.validateToken(id, token);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        User user = getUserFromToken(token);
        userRegistration(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User getUserFromToken(String token) {

        Token verificationToken = userService.getToken(token);

        return verificationToken.getUser();
    }

    private void userRegistration(User user) {

        user.setEnabled(true);
        userService.save(user);
    }

}
