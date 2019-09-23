package com.topGame.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class EmailValidator implements ConstraintValidator<ValidationEmail, String> {

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void initialize(final ValidationEmail constraintAnnotation) {}

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext context) {
        return (validateEmail(email));
    }

    private boolean validateEmail(final String email) {

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
