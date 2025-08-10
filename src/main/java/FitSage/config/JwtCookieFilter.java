package fitsage.config;

import fitsage.model.User;
import fitsage.services.UserService;
import fitsage.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtCookieFilter extends OncePerRequestFilter {

    @Autowired private JwtUtil jwtUtil;
    
    @Autowired private UserService userService;
    
    private static final String COOKIE_NAME = "token"; 

 @Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain)
        throws ServletException, IOException {

    String token = extractTokenFromCookies(request);

    if (token == null || token.isBlank()) {
        filterChain.doFilter(request, response);
        return;
    }

    try {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = jwtUtil.extractUsername(token); 

            if (username != null && jwtUtil.isTokenValid(token, username)) {
                User user = userService.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

                var authorities = java.util.List.of(
                    new SimpleGrantedAuthority("ROLE_" + user.getUserTypeEnum().name())
                );

                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user, null, authorities);

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
    } catch (Exception ex) {
        SecurityContextHolder.clearContext();
        
    }

    filterChain.doFilter(request, response);
}

private String extractTokenFromCookies(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null) return null;
    for (Cookie cookie : cookies) {
        if (COOKIE_NAME.equals(cookie.getName())) {
            return cookie.getValue();
        }
    }
    return null;
}


    // Optional: skip filter for public/auth endpoints
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String p = request.getRequestURI();
        return p.startsWith("/api/auth/login")
            || p.startsWith("/api/auth/logout")
            || p.startsWith("/api/public/");
    }
}
