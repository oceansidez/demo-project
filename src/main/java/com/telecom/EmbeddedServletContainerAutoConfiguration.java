package com.telecom;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.Servlet;
import java.io.File;

@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ConditionalOnWebApplication
public class EmbeddedServletContainerAutoConfiguration {
	
    @Configuration
    @ConditionalOnClass({ Servlet.class, Tomcat.class })
    @ConditionalOnMissingBean(value = ConfigurableWebServerFactory.class, search = SearchStrategy.CURRENT)
    public static class EmbeddedTomcat {
        @Bean
        public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        	TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
            // 若不设置BaseDirectory，每启动一次应用在IDE下会生成一个随机名称文件夹，无任何意义
        	tomcatServletWebServerFactory.setBaseDirectory(new File("D:/tomcat"));
        	tomcatServletWebServerFactory.setProtocol("org.apache.coyote.http11.Http11NioProtocol");
            return tomcatServletWebServerFactory;
        }
    }
}