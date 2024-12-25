package com.telecom.freemarker.method;

import com.telecom.annotation.FreemarkerMethod;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * Freemarker自定义方法示例
 *
 */
@FreemarkerMethod("sumMethod")
public class SumMethod implements TemplateMethodModelEx {

	@SuppressWarnings("rawtypes")
    public Object exec(List arguments) throws TemplateModelException {
		Integer sum = 0;
		if(arguments != null && arguments.size() > 0){
			for(Object arg : arguments){
				sum += (Integer) arg;
			}
		}
        return sum;
    }
}
