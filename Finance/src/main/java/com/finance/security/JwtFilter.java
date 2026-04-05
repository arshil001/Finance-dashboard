package com.finance.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {



        // Bearer 3jdjhdsjnzdnsj
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){


            try{
                token = authorizationHeader.substring(7);
                username = jwtUtil.getUsernameFromToken(token);

                if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if(jwtUtil.isTokenValid(token,userDetails)){

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }
                }
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }


        }else{
            System.out.println(" Invalid Authorization Header");
        }

        filterChain.doFilter(request,response);

    }
}