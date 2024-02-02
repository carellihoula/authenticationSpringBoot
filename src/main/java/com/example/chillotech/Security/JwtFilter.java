package com.example.chillotech.Security;

import com.example.chillotech.Entity.Jwt;
import com.example.chillotech.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {
    Jwt jwtDansLaBDD;
    private JwtService jwtService;
    private UserService userService;
    public JwtFilter(JwtService jwtService,UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String username = null;
        String token = null;
        boolean isExpiredToken = true;

        String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer")){
            //token = authorization.split(" ")[1];
            token = authorization.substring(7);
            jwtDansLaBDD = this.jwtService.findByToken(token);
            isExpiredToken = jwtService.isExpired(token);
            username = jwtService.extractUsername(token);
        }
        
        if(!isExpiredToken
                && username != null
                && jwtDansLaBDD.getUser().getEmail().equals(username)
                && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
