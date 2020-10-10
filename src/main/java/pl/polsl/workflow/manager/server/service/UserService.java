package pl.polsl.workflow.manager.server.service;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.view.UserPatch;
import pl.polsl.workflow.manager.server.view.UserPost;
import pl.polsl.workflow.manager.server.view.UserView;

public interface UserService {

    @NonNull
    UserView registerUser(UserPost userPost);

    UserView updateUser(Long userId, UserPatch userPatch);
}
