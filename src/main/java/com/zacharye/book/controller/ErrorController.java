package com.zacharye.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/error")
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getErrorPath() {
        return "error/error_common";
    }

    @RequestMapping
    public String error() {
        return getErrorPath();
    }
}

