package me.staek.aoppractice.config;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice // 예외를 낚아챔
public class MyExceptionHandler {

    @ExceptionHandler(value=IllegalArgumentException.class)
    public String badRequest(IllegalArgumentException e) {
        return e.getMessage();
    }

}
