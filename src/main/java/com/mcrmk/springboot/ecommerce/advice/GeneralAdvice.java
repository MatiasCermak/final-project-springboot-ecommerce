package com.mcrmk.springboot.ecommerce.advice;

import com.mcrmk.springboot.ecommerce.exception.AuthenticationException;
import com.mcrmk.springboot.ecommerce.model.response.UserResponse;
import com.mcrmk.springboot.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class GeneralAdvice {

    private UserService userService;

    @Pointcut("execution(* com.mcrmk.springboot.ecommerce.controller.*.*(..))")
    void controllerMethodsIntercept() {
    }

    @Pointcut("@annotation(com.mcrmk.springboot.ecommerce.annotation.AdminPermission)")
    void adminPermission() {
    }

    ;


    @After("controllerMethodsIntercept()")
    void afterControllerMethodIntercept(JoinPoint jp) {
        log.info("Executed " + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName() + "()");
    }

    @AfterThrowing(pointcut = "controllerMethodsIntercept()", throwing = "e")
    void afterThrowingControllerMethodIntercept(JoinPoint jp, Exception e) {
        log.error("Exception with message: " + e.getMessage() + " captured while executing " + jp.getSignature().getName() + "().");
    }

    @Before("adminPermission()")
    void adminPermissionFilter() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            UserResponse user = userService.retrieveByUsername(username);
            if (user.getIsAdmin()) throw new AuthenticationException("The user is not an admin");
        } catch (NullPointerException ex) {
            throw new AuthenticationException("The user is not an admin");
        }
    }

}
