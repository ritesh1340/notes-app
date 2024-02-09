package com.example.notesapp.interceptor;

import com.example.notesapp.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
public class AuthorizationAndAuthentication implements HandlerInterceptor {

    private final UserServiceImpl userService;

    public AuthorizationAndAuthentication(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String userID = pathVariables.get("userID").toString();
        userService.validateToken(userID, token).toCompletableFuture().join();
        System.out.println("[APP-INFO]: Validation Successful for userID - " + userID);
        return Boolean.TRUE;
    }
}
