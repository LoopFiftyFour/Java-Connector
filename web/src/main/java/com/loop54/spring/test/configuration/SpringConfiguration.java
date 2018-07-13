package com.loop54.spring.test.configuration;

import com.loop54.ILoop54ClientProvider;
import com.loop54.Loop54ClientProvider;
import com.loop54.Loop54SettingsCollection;
import com.loop54.spring.SpringRemoteClientInfoProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

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
    public ILoop54ClientProvider loop54ClientProvider() {
        /*
        This creates a new client provider with only one setting. The SpringRemoteClientInfoProvider will take the
        RequestContextHolder.currentRequestAttributes() and use that to serve user data to the Loop54 library.
        */
        return new Loop54ClientProvider(
                new SpringRemoteClientInfoProvider(),
                Loop54SettingsCollection.create()
                        .add("english", "https://helloworld.54proxy.com"));
    }
}
