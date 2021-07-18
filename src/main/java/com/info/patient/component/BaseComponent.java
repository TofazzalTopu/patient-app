package com.info.patient.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;


/**
 * @author Tofazzal Hossain
 * @created on 15/07/21 9:19 AM
 */

@Component
public abstract class BaseComponent {

    protected String message;
    private Locale locale = LocaleContextHolder.getLocale();

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String message) {
        try {
            return message.contains(".") ? messageSource.getMessage(message, null, locale) : message;
        } catch (NoSuchMessageException ex) {
            return message;
        }
    }

    public String getMessage(String message, Object[] objects) {
        try {
            return message.contains(".") ? messageSource.getMessage(message, objects, locale) : message;
        } catch (NoSuchMessageException ex) {
            return message;
        }
    }

}
