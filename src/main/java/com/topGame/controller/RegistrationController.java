package com.topGame.controller;

import com.topGame.entity.User;
import com.topGame.entity.Token;
import com.topGame.service.SecurityService;
import com.topGame.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import java.util.Locale;


public class RegistrationController {

    private UserServiceImpl userService;
    private MessageSource messages;
    private SecurityService securityService;

    public static final String WELCOME_PAGE="welcome";

    @Autowired
    public RegistrationController(UserServiceImpl userService, MessageSource messages,
                                  SecurityService securityService) {

        this.userService = userService;
        this.messages = messages;
        this.securityService=securityService;
    }

    @GetMapping(value = "/registration/confirm")
    public String confirmRegistration(HttpServletRequest request, Model model, @RequestParam("id") Long id,
                                      @RequestParam("token") String token) {

        Locale locale = request.getLocale();
        String result = securityService.validateToken(id, token);
        if (result!= null) {
            String message = messages.getMessage("registration.message"+result, null, locale);
            model.addAttribute("message", message);
            return "redirect:/confirmationError.html?lang=" + locale.getLanguage();
        }

        User user = getUserFromToken(token);
        userRegistration(user);

        return WELCOME_PAGE;
    }

    private User getUserFromToken(String token){

        Token verificationToken = userService.getToken(token);

        return verificationToken.getUser();
    }

    private void userRegistration(User user){

        user.setEnabled(true);
        userService.save(user);
    }

}
