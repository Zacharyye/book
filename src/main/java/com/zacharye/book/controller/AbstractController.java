package com.zacharye.book.controller;

import com.zacharye.book.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractController {
     protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({RuntimeException.class})
    public String exceptionHandler () {
        return "error/error_common";
    }
}
