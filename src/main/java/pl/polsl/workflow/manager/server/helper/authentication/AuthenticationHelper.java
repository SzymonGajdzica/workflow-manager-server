package pl.polsl.workflow.manager.server.helper.authentication;

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

import java.time.Instant;
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

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws RuntimeException {
        String parsedToken;
        if (token != null && token.startsWith("Bearer "))
            parsedToken = token.substring(7);
        else
            parsedToken = token;
        return claimsResolver.apply(Jwts.parser().setSigningKey(secret).parseClaimsJws(parsedToken).getBody());
    }

    @NonNull
    public String generateToken(String username, String plainPassword) throws RuntimeException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, plainPassword));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(validity)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public<T extends User> T getUserFromToken(String token) {
        String userName;
        try {
            userName = getUsernameFromToken(token);
        } catch (RuntimeException e) {
            throw new NotAuthorizedException(e.getMessage());
        }
        return (T) userRepository.findByUsername(userName).orElseThrow(() -> new NotAuthorizedException("Token does not match any user"));
    }

    @NonNull
    public String getUsernameFromToken(String token) throws RuntimeException {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @NonNull
    public Instant getExpirationDateFromToken(String token) throws RuntimeException {
        return getClaimFromToken(token, Claims::getExpiration).toInstant();
    }

    @NonNull
    public Boolean validateToken(String token, UserDetails userDetails) throws RuntimeException {
        return getUsernameFromToken(token).equals(userDetails.getUsername()) && !getExpirationDateFromToken(token).isBefore(Instant.now());
    }

}
