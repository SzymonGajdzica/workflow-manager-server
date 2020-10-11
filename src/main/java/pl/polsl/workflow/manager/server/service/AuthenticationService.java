package pl.polsl.workflow.manager.server.service;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.view.AuthenticationView;

public interface AuthenticationService {

    @NonNull
    AuthenticationView getAuthenticationDetails(@NonNull String username, @NonNull String plainPassword);

}
