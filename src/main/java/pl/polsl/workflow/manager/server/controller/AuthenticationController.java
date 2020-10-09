package pl.polsl.workflow.manager.server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.workflow.manager.server.service.AuthenticationService;
import pl.polsl.workflow.manager.server.view.AuthenticationView;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping(value = "/createAuthenticationToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationView createAuthenticationToken(@RequestParam String username, @RequestParam String plainPassword) {
        return authenticationService.getAuthenticationDetails(username, plainPassword);
    }

}