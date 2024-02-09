package com.example.notesapp.config;

import com.example.notesapp.interceptor.AuthorizationAndAuthentication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private final AuthorizationAndAuthentication authorizationAndAuthentication;

    public AppConfig(AuthorizationAndAuthentication authorizationAndAuthentication) {
        this.authorizationAndAuthentication = authorizationAndAuthentication;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationAndAuthentication).addPathPatterns("/api/users/{userID}/notes/**");
    }
}
