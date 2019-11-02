package org.softuni.residentevil.config;

import org.softuni.residentevil.web.interceptors.EditInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final EditInterceptor editInterceptor;

    @Autowired
    public InterceptorConfig(EditInterceptor editInterceptor) {
        this.editInterceptor = editInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.editInterceptor)
                .addPathPatterns("/viruses/edit/*");
    }
}
