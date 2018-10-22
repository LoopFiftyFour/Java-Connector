package com.loop54.spring.test.codeexamples.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.loop54.ILoop54Client;
import com.loop54.Loop54Client;
import com.loop54.Loop54Settings;
import com.loop54.http.RequestManager;
import com.loop54.spring.SpringRemoteClientInfoProvider;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.loop54.spring.test")
public class SpringConfiguration {
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }    
    
    @Bean 
    public ILoop54Client loop54Client()
    {    	
    	/*
        This creates a new client with only one setting. The SpringRemoteClientInfoProvider will take the
        RequestContextHolder.currentRequestAttributes() and use that to serve user data to the Loop54 library.
        */
        return new Loop54Client(new RequestManager(new Loop54Settings("https://helloworld.54proxy.com", null)), 
        		new SpringRemoteClientInfoProvider());
    }
}
