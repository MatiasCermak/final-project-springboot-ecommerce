package com.mcrmk.springboot.ecommerce.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class GeneralAdvice {

    @Pointcut("execution(* com.mcrmk.springboot.ecommerce.controller.*.*(..))")
    void controllerMethodsIntercept(){};

    @After("controllerMethodsIntercept()")
    void afterControllerMethodIntercept(JoinPoint jp){
        log.info("Executed " + jp.getSignature().getName() + "().");
    }

    @AfterThrowing("controllerMethodsIntercept()")
    void afterThrowingControllerMethodIntercept(JoinPoint jp){
        log.error("Exception captured while executing " + jp.getSignature().getName() + "().");
    }

}
