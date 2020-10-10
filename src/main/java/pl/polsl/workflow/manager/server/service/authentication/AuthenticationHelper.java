package pl.polsl.workflow.manager.server.service.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.configuration.Parameters;
import pl.polsl.workflow.manager.server.exception.NotAuthorizedException;
import pl.polsl.workflow.manager.server.model.User;
import pl.polsl.workflow.manager.server.repository.UserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class AuthenticationHelper {

    @Value(Parameters.Authorization.VALIDITY)
    private Long validity;

    @Value(Parameters.Authorization.SECRET)
    private String secret;

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public AuthenticationHelper(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        String parsedToken;
        if (token != null && token.startsWith("Bearer "))
            parsedToken = token.substring(7);
        else
            parsedToken = token;
        return claimsResolver.apply(Jwts.parser().setSigningKey(secret).parseClaimsJws(parsedToken).getBody());
    }

    @NonNull
    public String generateToken(String username, String plainPassword) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, plainPassword));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public<T extends User> T getUserFromToken(String token) {
        String userName;
        try {
            userName = getUsernameFromToken(token);
        } catch (Exception e) {
            throw new NotAuthorizedException(e.getMessage());
        }
        return (T) userRepository.findByUsername(userName).orElseThrow(() -> new NotAuthorizedException("Token does not match any user"));
    }

    @NonNull
    public String getUsernameFromToken(String token) throws Exception {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @NonNull
    public Date getExpirationDateFromToken(String token) throws Exception {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    @NonNull
    public Boolean validateToken(String token, UserDetails userDetails) throws Exception {
        return getUsernameFromToken(token).equals(userDetails.getUsername()) && !getExpirationDateFromToken(token).before(new Date());
    }

}