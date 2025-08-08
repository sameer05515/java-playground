package com.p.smbg;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@ControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(IllegalArgumentException.class)
 public String notFound(IllegalArgumentException e,Model m){m.addAttribute("message",e.getMessage());return "error";}
}