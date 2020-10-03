package org.zerock.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j;


// ControllerAdvice 템플릿
@Log4j
@ControllerAdvice
public class DefaultControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception ex) {
        log.error("에러임 Exception: {" +  ex.toString() + "}");
        printExceptionInfo(request, ex);
        ModelAndView mv =new ModelAndView("error");
        mv.addObject("result","9999");
        mv.addObject("message", ex.getMessage());
        return mv;
    }
 
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(HttpServletRequest request, RuntimeException ex) {
        log.error("에러임 RuntimeException: {" +  ex.toString() + "}");
        printExceptionInfo(request, ex);
        ModelAndView mv =new ModelAndView("error");
        mv.addObject("result","8888");
        mv.addObject("message", ex.getMessage());
        return mv;
    }
     
    private void printExceptionInfo(HttpServletRequest request, Exception ex){
        log.error("PARMAS: {" + request.getParameterMap() + "}");
        for( StackTraceElement s : ex.getStackTrace() ){
            log.error("STACK: {" +  s + "}");
        }
    }
 
}


