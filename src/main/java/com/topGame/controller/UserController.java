package com.topGame.controller;


import com.topGame.entity.User;
import com.topGame.registration.OnRegistrationCompleteEvent;
import com.topGame.service.SecurityService;
import com.topGame.service.UserService;
import com.topGame.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


@RestController
public class UserController {

    private UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;
    private ApplicationEventPublisher eventPublisher;
    private JavaMailSender mailSender;

    public static final String REGISTRATION_PAGE = "registration";


    @Autowired
    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator,
                          ApplicationEventPublisher eventPublisher, JavaMailSender mailSender) {

        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.eventPublisher = eventPublisher;
        this.mailSender = mailSender;

    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {

        model.addAttribute("userForm", new User());

        return REGISTRATION_PAGE;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity registration(@RequestBody User userForm, BindingResult bindingResult,
                                       HttpServletRequest request) {

        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User registeredUser = userService.save(userForm);

        confirmRegistration(registeredUser, request);
        authorisation(userForm);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<String> login(String error, String logout) {

        if (error != null) {
            return new ResponseEntity<>("Username or password is incorrect.", HttpStatus.BAD_REQUEST);
        }
        if (logout != null) {
            return new ResponseEntity<>("Logged out successfully.", HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/user/resetPassword")
    public void resetPassword(HttpServletRequest request, @RequestParam("email") String email) {

        User user = userService.findUserByEmail(email);

        if (user != null) {
            mailSender.send(userService.constructResetTokenMessage(request.getContextPath(),
                    request.getLocale(), createToken(user), user));
        }
    }

    @GetMapping(value = "/user/changePassword")
    public ResponseEntity<String> showChangePasswordPage(@PathVariable Long id,
                                                         @RequestParam("token") String token) {

        String result = securityService.validateToken(id, token);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/user/savePassword")
    public void savePassword(HttpServletRequest request, String password) {

        User user = (User) request.getUserPrincipal();
        userService.changeUserPassword(user, password);
    }

    private String createUrl(HttpServletRequest request) {

        return request.getContextPath();
    }

    private void confirmRegistration(User registeredUser, HttpServletRequest request) {

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, request.getLocale(), createUrl(request)));
    }

    private void authorisation(User userForm) {
        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
    }

    private String createToken(User user) {

        String token = UUID.randomUUID().toString();
        userService.saveToken(user, token);

        return token;
    }

}
