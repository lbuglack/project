package com.topGame.service;

import com.topGame.entity.Token;
import com.topGame.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Calendar;


@Service
public class SecurityServiceImpl implements SecurityService {

    private  AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private TokenRepository tokenRepository;

    public static final String INVALID_TOKEN="invalidToken";
    public static final String EXPIRED="expired";

    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                               TokenRepository tokenRepository) {

        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenRepository=tokenRepository;
    }

    @Override
    public String findLoggedInUser() {

        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }

        return null;
    }

    @Override
    public void autoLogin(String username, String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    @Override
    public String validateToken(long id, String token) {

        Token validToken = tokenRepository.findByToken(token);

        if ((validToken == null) || (validToken.getUser().getId() != id)) {
            return INVALID_TOKEN;
        }
        Calendar cal = Calendar.getInstance();
        if ((validToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return EXPIRED;
        }

        return null;
    }
}

