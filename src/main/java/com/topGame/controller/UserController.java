package com.topGame.controller;


import com.topGame.entity.Token;
import com.topGame.entity.User;
import com.topGame.registration.OnRegistrationCompleteEvent;
import com.topGame.service.SecurityService;
import com.topGame.service.UserService;
import com.topGame.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.UUID;


@RestController
public class UserController {

    private  UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;
    private ApplicationEventPublisher eventPublisher;
    private JavaMailSender mailSender;
    private MessageSource messages;

    public static final String REGISTRATION_PAGE="registration";
    public static final String LOGIN_PAGE="registration";

    @Autowired
    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator,
                          ApplicationEventPublisher eventPublisher, JavaMailSender mailSender,MessageSource messages) {

        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.eventPublisher=eventPublisher;
        this.mailSender=mailSender;
        this.messages=messages;

    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {

        model.addAttribute("userForm", new User());

        return REGISTRATION_PAGE;
    }

    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
                               HttpServletRequest request) {

        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return REGISTRATION_PAGE;
        }

        User registeredUser=userService.save(userForm);

        confirmRegistration(registeredUser,request);
        authorisation(userForm);


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

        return LOGIN_PAGE;
    }

    @PostMapping(value = "/user/resetPassword")
    public void resetPassword(HttpServletRequest request, @RequestParam("email") String email) {

        User user = userService.findUserByEmail(email);

        if (user != null) {
            mailSender.send(userService.constructResetTokenEmail(request.getContextPath(),
                    request.getLocale(), createToken(user), user));
        }
    }


    @GetMapping(value = "/user/changePassword")
    public String showChangePasswordPage(Locale locale, Model model, @RequestParam("id") long id,
                                         @RequestParam("token") String token) {

        String result = securityService.validateToken(id, token);
        if (result != null) {
            String message = messages.getMessage("registration.message"+result, null, locale);
            model.addAttribute("message", message);
            return "redirect:/login?lang=" + locale.getLanguage();
        }

        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }

    //what return?????????????????
    @PostMapping(value = "/user/savePassword")
    public void savePassword(HttpServletRequest request,Locale locale, @Valid String password) {

        User user = (User)request.getUserPrincipal();

        userService.changeUserPassword(user, password);
//        return new GenericResponse(
//                messages.getMessage("message.resetPasswordSuc", null, locale));
    }

    private String createUrl(HttpServletRequest request){

        return request.getContextPath();
    }

    private void confirmRegistration(User registeredUser, HttpServletRequest request){

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser,request.getLocale(), createUrl(request)));
    }

    private void authorisation(User userForm){
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
    }

    private String createToken(User user){

        String token = UUID.randomUUID().toString();
        userService.saveToken(user, token);

        return token;
    }

}
