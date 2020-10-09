package pl.polsl.workflow.manager.server.mapper;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.model.User;
import pl.polsl.workflow.manager.server.view.UserPost;
import pl.polsl.workflow.manager.server.view.UserView;

public interface UserMapper {

    @NonNull
    User map(UserPost userPost);

    UserView map(User user);

}
