package pl.polsl.workflow.manager.server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.workflow.manager.server.service.UserService;
import pl.polsl.workflow.manager.server.view.UserPost;
import pl.polsl.workflow.manager.server.view.UserView;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserView registerUser(@Valid @RequestBody UserPost userPost) {
        return userService.registerUser(userPost);
    }

}
