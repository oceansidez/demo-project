package com.telecom.freemarker.method;

import com.telecom.annotation.FreemarkerMethod;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

@FreemarkerMethod("messageForI18n")
public class MessageForI18nMethod implements TemplateMethodModelEx {

	@Autowired
	MessageSource messageSource;
	
	@SuppressWarnings("rawtypes")
    public Object exec(List arguments) throws TemplateModelException {
		return messageSource.getMessage(arguments.get(0).toString(), null, Locale.CHINA);
    }
}