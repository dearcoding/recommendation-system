package com.dearcoding.recommendationsystem.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut for all methods in controllers
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    /**
     * Pointcut for all methods in services
     */
    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceMethods() {}

    /**
     * Logs before method execution
     */
    @Before("controllerMethods() || serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String className = signature.getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        logger.info("Entering method: {}.{}() with arguments: {}", className, methodName, Arrays.toString(args));
    }

    /**
     * Logs after method execution
     */
    @AfterReturning(value = "controllerMethods() || serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String className = signature.getDeclaringTypeName();

        logger.info("Exiting method: {}.{}() with result: {}", className, methodName, result);
    }

    /**
     * Logs when an exception occurs
     */
    @AfterThrowing(value = "controllerMethods() || serviceMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String className = signature.getDeclaringTypeName();

        logger.error("Exception in {}.{}() with message: {}", className, methodName, ex.getMessage(), ex);
    }
}
