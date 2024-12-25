package com.telecom.config;

import com.telecom.annotation.FreemarkerMethod;
import freemarker.cache.ClassTemplateLoader;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.TemplateException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Freemarker配置类
 * 
 */
@Configuration
public class FreemarkerConfig implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;

	@Autowired
	public WebConfig webConfig;
	
	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
	
    @Bean
    @Autowired
    public freemarker.template.Configuration freeMarkerConfig(ServletContext servletContext) throws IOException,
            TemplateException {
        FreeMarkerConfigurer freemarkerConfig = configFreeMarkerConfigurer(servletContext);
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(FreemarkerMethod.class);
        freemarker.template.Configuration configuration = freemarkerConfig.getConfiguration();
        for (String key : map.keySet()) {
            configuration.setSharedVariable(key, map.get(key));
        }
        return freemarkerConfig.getConfiguration();
    }
    
    @Bean
    public freemarker.template.Configuration writerFileConfig(ServletContext servletContext) throws IOException,
            TemplateException {
        FreeMarkerConfigurer freemarkerConfig = writerConfigFreeMarkerConfigurer(servletContext);
        return freemarkerConfig.getConfiguration();
    }

    @Bean
    @Autowired
    public TaglibFactory taglibFactory(ServletContext servletContext) throws IOException, TemplateException {
        FreeMarkerConfigurer freemarkerConfig = configFreeMarkerConfigurer(servletContext);
        TaglibFactory taglibFactory = freemarkerConfig.getTaglibFactory();
        taglibFactory.setObjectWrapper(freemarker.template.Configuration.getDefaultObjectWrapper(freemarker.template.Configuration.getVersion()));
        String[] tlds = webConfig.getTlds().split(",");
        List<String> classPathTlds = new ArrayList<String>();
        for(String tld : tlds){
        	classPathTlds.add(webConfig.getTldPath() + tld);
        }
        taglibFactory.setClasspathTlds(classPathTlds);
        return taglibFactory;
    }

    @Bean
    @Autowired
    public FreeMarkerConfig springFreeMarkerConfig(ServletContext servletContext) throws IOException, TemplateException {
        return new MyFreeMarkerConfig(freeMarkerConfig(servletContext), taglibFactory(servletContext));
    }

    @Bean
    public FreeMarkerViewResolver viewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setCache(false);
        viewResolver.setSuffix(".ftl");
        viewResolver.setContentType("text/html;charset=UTF-8");
        return viewResolver;
    }

    private static class MyFreeMarkerConfig implements FreeMarkerConfig {

        private final freemarker.template.Configuration configuration;
        private final TaglibFactory taglibFactory;

        private MyFreeMarkerConfig(freemarker.template.Configuration configuration, TaglibFactory taglibFactory) {
            this.configuration = configuration;
            this.taglibFactory = taglibFactory;
        }

        @Override
        public freemarker.template.Configuration getConfiguration() {
            return configuration;
        }

        @Override
        public TaglibFactory getTaglibFactory() {
            return taglibFactory;
        }
        
    }
    
    private static class ServletContextResourceHandler implements InvocationHandler {

        private final ServletContext target;

        private ServletContextResourceHandler(ServletContext target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("getResourceAsStream".equals(method.getName())) {
                Object result = method.invoke(target, args);
                if (result == null) {
                    result = FreemarkerConfig.class.getResourceAsStream((String) args[0]);
                }
                return result;
            } else if ("getResource".equals(method.getName())) {
                Object result = method.invoke(target, args);
                if (result == null) {
                    result = FreemarkerConfig.class.getResource((String) args[0]);
                }
                return result;
            }

            return method.invoke(target, args);
        }
    }
    
	private static FreeMarkerConfigurer configFreeMarkerConfigurer(
			ServletContext servletContext) throws IOException,
			TemplateException {
		FreeMarkerConfigurer freemarkerConfig = new FreeMarkerConfigurer();
		freemarkerConfig.setPreTemplateLoaders(new ClassTemplateLoader(
				FreemarkerConfig.class, "/templates/"));
		ServletContext servletContextProxy = (ServletContext) Proxy
				.newProxyInstance(
						ServletContextResourceHandler.class.getClassLoader(),
						new Class<?>[] { ServletContext.class },
						new ServletContextResourceHandler(servletContext));
		freemarkerConfig.setServletContext(servletContextProxy);
		Properties settings = new Properties();
		settings.put("default_encoding", "UTF-8");
		freemarkerConfig.setFreemarkerSettings(settings);
		freemarkerConfig.afterPropertiesSet();
		return freemarkerConfig;
	}

	private static FreeMarkerConfigurer writerConfigFreeMarkerConfigurer(
			ServletContext servletContext) throws IOException,
			TemplateException {
		FreeMarkerConfigurer freemarkerConfig = new FreeMarkerConfigurer();
		freemarkerConfig.setPreTemplateLoaders(new ClassTemplateLoader(
				FreemarkerConfig.class, "/static/template/"));
		ServletContext servletContextProxy = (ServletContext) Proxy
				.newProxyInstance(
						ServletContextResourceHandler.class.getClassLoader(),
						new Class<?>[] { ServletContext.class },
						new ServletContextResourceHandler(servletContext));
		freemarkerConfig.setServletContext(servletContextProxy);
		freemarkerConfig.setServletContext(servletContextProxy);
		Properties settings = new Properties();
		settings.put("default_encoding", "UTF-8");
		freemarkerConfig.setFreemarkerSettings(settings);
		freemarkerConfig.afterPropertiesSet();
		return freemarkerConfig;
	}
}