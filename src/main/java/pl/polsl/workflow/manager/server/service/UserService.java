package pl.polsl.workflow.manager.server.service;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.view.UserPatch;
import pl.polsl.workflow.manager.server.view.UserPost;
import pl.polsl.workflow.manager.server.view.UserView;

import java.util.List;

public interface UserService {

    @NonNull
    UserView registerUser(@NonNull UserPost userPost);

    @NonNull
    UserView updateUser(@NonNull Long userId, @NonNull UserPatch userPatch);

    @NonNull
    List<UserView> getAllUsers();

    @NonNull
    UserView getSelf(@NonNull String userToken);
}
