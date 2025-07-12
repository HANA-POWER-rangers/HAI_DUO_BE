package com.poweranger.hai_duo.global.config.security;

import com.poweranger.hai_duo.user.domain.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TempUserAuthenticationFilter implements Filter {

    private final UserRepository userRepository;

    private static final List<String> WHITELIST = List.of(
            "/swagger-ui", "/v3/api-docs", "/swagger-resources", "/webjars",
            "/swagger-ui.html", "/graphql", "/api/users/temp"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();

        if (WHITELIST.stream().anyMatch(uri::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            userRepository.findByTempUserToken(token).ifPresent(user -> {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("tempUser", user);
            });
        }

        chain.doFilter(request, response);
    }
}
