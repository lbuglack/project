package com.topGame.controller;

import com.topGame.entity.User;
import com.topGame.entity.VerificationToken;
import com.topGame.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;
import java.util.Locale;


public class RegistrationController {


    private UserServiceImpl userService;
    private MessageSource messages;

    @Autowired
    public RegistrationController(UserServiceImpl userService, MessageSource messages) {
        this.userService = userService;
        this.messages = messages;
    }

    @GetMapping(value = "/registration/confirm")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        //token validation
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("registration.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/confirmationError.html?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("registration.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return "redirect:/confirmationError.html?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        userService.save(user);

        return "welcome";
    }

}
