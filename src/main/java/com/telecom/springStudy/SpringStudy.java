package com.telecom.springStudy;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Spring 启动和 bean 的导入
 */
public class SpringStudy {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        // 注册后可以识别config和bean注解，bean工厂后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean("myConfig", MyConfig.class);
        // spring 初始化
        context.refresh();
        System.out.println("context = " + context);
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }

    @Configuration
    //    @Import(Bean2.class) // 1.直接引用，不需要加 @Bean
    //    @Import(MyConfig2.class) // 2.引用配置类，会将配置类中所有的Bean给加载进去
    //    @Import(MySelector.class) // 3.实现 ImportSelector\DeferredImportSelector 接口,然后导入
    @Import(MyRegistrar.class) // 4.实现 ImportBeanDefinitionRegistrar 接口,然后导入
    static class MyConfig {
        @Bean
        public Bean1 bean1() {
            System.out.println("Bean1初始化成功");
            return new Bean1();
        }
    }

    static class MyRegistrar implements ImportBeanDefinitionRegistrar {
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            //创建一个 beandefinition
            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Bean5.class).getBeanDefinition();
            registry.registerBeanDefinition("bean5", beanDefinition);
        }
    }

    static class MySelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            System.out.println("Bean4.class.getName() = " + Bean4.class.getName());
            return new String[]{Bean4.class.getName()};
        }
    }

    @Configuration
    static class MyConfig2 {
        @Bean
        public Bean2 bean2() {
            System.out.println("Bean2初始化成功");
            return new Bean2();
        }

        @Bean
        public Bean3 bean3() {
            System.out.println("Bean3初始化成功");
            return new Bean3();
        }
    }

    static class Bean1 {

    }

    static class Bean2 {

    }

    static class Bean3 {

    }

    static class Bean4 {

    }

    static class Bean5 {

    }

}
