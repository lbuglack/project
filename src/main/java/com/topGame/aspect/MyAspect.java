package com.topGame.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(* com.topGame.service.UserServiceImpl.save(..))")
    public void callAtMyServiceAnnotation() {
    }

    @Before("callAtMyServiceAnnotation()")
    public void beforeCallAt() {
        System.out.println("Begin save user in database");
    }
}
