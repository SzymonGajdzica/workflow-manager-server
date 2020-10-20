package pl.polsl.workflow.manager.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.workflow.manager.server.configuration.Parameters;
import pl.polsl.workflow.manager.server.service.UserService;
import pl.polsl.workflow.manager.server.view.UserPatch;
import pl.polsl.workflow.manager.server.view.UserPost;
import pl.polsl.workflow.manager.server.view.UserView;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserView registerUser(@Valid @RequestBody UserPost userPost) {
        return userService.registerUser(userPost);
    }

    @PatchMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserView updateUser(@PathVariable Long userId, @Valid @RequestBody UserPatch userPatch) {
        return userService.updateUser(userId, userPatch);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserView> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "self", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView getSelf(
            @ApiIgnore @RequestHeader(Parameters.Authorization.HEADER) String token
    ) {
        return userService.getSelf(token);
    }

}
