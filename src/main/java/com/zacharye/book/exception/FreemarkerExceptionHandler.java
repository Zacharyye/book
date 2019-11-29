package com.zacharye.book.exception;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

public class FreemarkerExceptionHandler implements TemplateExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleTemplateException(TemplateException e, Environment environment, Writer writer) throws TemplateException {
        logger.error("[Freemarker error: " + e.getMessage() + "]");
        String missingVariable = "undefined";
        try {
            String[] tmp = e.getMessageWithoutStackTop().split("\n");
            if (tmp.length > 1) {
                tmp = tmp[1].split(" ");
            }
            if (tmp.length > 1) {
                missingVariable = tmp[1];
            }
            writer.write("");
            logger.error("出错了，请联系管理员", e);
        } catch (IOException oe) {
            throw new TemplateException("Failed to print error message. Cause: " + oe, environment);
        }
    }
}
