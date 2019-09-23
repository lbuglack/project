package com.topGame.registration;

import com.topGame.entity.User;
import com.topGame.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.UUID;

public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

        private UserServiceImpl userService;
        private MessageSource messages;
        private JavaMailSender mailSender;

        @Autowired
        public RegistrationListener(UserServiceImpl userService, MessageSource messages, JavaMailSender mailSender) {

                this.userService=userService;
                this.messages = messages;
                this.mailSender = mailSender;
        }

        @Override
        public void onApplicationEvent(OnRegistrationCompleteEvent event) {
                this.confirmRegistration(event);
        }

        private void confirmRegistration(OnRegistrationCompleteEvent event) {

                User user = event.getUser();
                String token=createToken(user);

                sendConfirmationEmail(user,event,token);
        }

        private String createToken(User user){

                String token = UUID.randomUUID().toString();
                userService.saveToken(user, token);

                return token;
        }

        private void sendConfirmationEmail(User user, OnRegistrationCompleteEvent event, String token){

                String recipientAddress = user.getEmail();
                String subject = "Registration Confirmation";
                String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
                String message = messages.getMessage("message.registration.success", null, event.getLocale());

                SimpleMailMessage email = new SimpleMailMessage();
                email.setTo(recipientAddress);
                email.setSubject(subject);
                email.setText(message + " rn" + "http://localhost:8080" + confirmationUrl);
                mailSender.send(email);
        }
}
