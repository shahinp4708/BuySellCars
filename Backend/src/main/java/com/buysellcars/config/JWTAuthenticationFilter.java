package com.buysellcars.config;

import com.buysellcars.services.jwt.UserService;
import com.buysellcars.utils.jwt.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserService userService;
    @Override
    protected void doFilterInternal(@NotNull  HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(org.apache.commons.lang3.StringUtils.isEmpty(authHeader) || !org.apache.commons.lang3.StringUtils.startsWith(authHeader,"Bearer") ){
            filterChain.doFilter(request,response);
            return;
        }
        jwt=authHeader.substring(7);
        userEmail = jwtUtil.extractUserName(jwt);

        if(StringUtils.isNoneEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
            if(jwtUtil.isTokenValid(jwt,userDetails)){
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails((new WebAuthenticationDetailsSource().buildDetails(request)));
                context.setAuthentication(usernamePasswordAuthenticationToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request,response);
    }
}
