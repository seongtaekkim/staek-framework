package me.staek.aoppractice.config;

import io.sentry.Sentry;
import jakarta.servlet.http.HttpServletRequest;
import me.staek.aoppractice.domain.CommonDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;

@Component
@Aspect
public class BindingAdvice {

    private static final Logger log = LoggerFactory.getLogger(BindingAdvice.class);

    @Before("execution(* me.staek.aoppractice.web..*Controller.*(..))")
    public void beforeTest() {
        System.out.println("before logging....");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        System.out.println(request.getRequestURL());
    }

    @After("execution(* me.staek.aoppractice.web..*Controller.*(..))")
    public void afterTest() {
        System.out.println("after logging....");
    }

    // 함수실행 전 후로 실행된다.
    // ProceedingJoinPoint 메서드의 모든 정보를 가져온다.
    @Around("execution(* me.staek.aoppractice.web..*Controller.*(..))")
    public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();

        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                    var map = new HashMap<>();

                    for (FieldError fe : bindingResult.getFieldErrors()) {
                        map.put(fe.getField(), fe.getDefaultMessage());
                        Sentry.captureMessage(type + "."+ method + "()=> field: " + fe.getField() + ",메세지: " + fe.getDefaultMessage());
                        log.warn(type + "."+ method + "()=> field: " + fe.getField() + ",메세지: " + fe.getDefaultMessage());
                    }
                    return new CommonDto<>(HttpStatus.BAD_REQUEST.value(), map);
                }
            }
        }
//        System.out.println(type);
//        System.out.println(method);
        return proceedingJoinPoint.proceed(); // 함수의 스택을 이어서 실행
    }
}
