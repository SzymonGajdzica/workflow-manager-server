package pl.polsl.workflow.manager.server.service;

import org.springframework.stereotype.Service;
import pl.polsl.workflow.manager.server.exception.NotAuthorizedException;
import pl.polsl.workflow.manager.server.helper.authentication.AuthenticationHelper;
import pl.polsl.workflow.manager.server.view.AuthenticationView;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationHelper authenticationHelper;

    public AuthenticationServiceImpl(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public AuthenticationView getAuthenticationDetails(String username, String plainPassword) {
        try {
            String token = authenticationHelper.generateToken(username, plainPassword);
            AuthenticationView authenticationView = new AuthenticationView();
            authenticationView.setToken(token);
            authenticationView.setExpirationDate(authenticationHelper.getExpirationDateFromToken(token));
            return authenticationView;
        }  catch (Exception e){
            throw new NotAuthorizedException("Wrong credentials");
        }
    }

}
