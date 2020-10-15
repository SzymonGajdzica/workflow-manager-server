package pl.polsl.workflow.manager.server.helper.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomOncePerRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final AuthenticationHelper authenticationHelper;

    public CustomOncePerRequestFilter(CustomUserDetailsService userDetailsService, AuthenticationHelper authenticationHelper) {
        this.userDetailsService = userDetailsService;
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");
            String username = authenticationHelper.getUsernameFromToken(token);
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (authenticationHelper.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (Exception e) {
            System.out.println("Token filter exception = " + e.getClass().getName() + " - " + e.getMessage());
        }
        chain.doFilter(request, response);
    }
}