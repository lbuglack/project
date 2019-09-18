package com.topGame.controller;


import com.topGame.entity.User;
import com.topGame.registration.OnRegistrationCompleteEvent;
import com.topGame.service.SecurityService;
import com.topGame.service.UserService;
import com.topGame.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;



@Controller
public class UserController {

    private  UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserController(UserService userService, SecurityService securityService,
                          UserValidator userValidator,ApplicationEventPublisher eventPublisher) {

        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.eventPublisher=eventPublisher;

    }


    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
                               WebRequest request) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        User registered=userService.save(userForm);

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,request.getLocale(), appUrl));

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/welcome";
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

//    @GetMapping(value = {"/", "/welcome"})
//    public String welcome(Model model) {
//        return "welcome";
//    }
//
//    @GetMapping(value = "/admin")
//    public String admin(Model model) {
//        return "admin";
//    }
}
