package pl.jakubmuczyn.controller;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE) // HIGHEST_PRECEDENCE jest domyślną wartością
@Component
public class LoggerFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);
    
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
    }
}
