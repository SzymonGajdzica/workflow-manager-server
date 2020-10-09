package pl.polsl.workflow.manager.server.mapper;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.exception.NotFoundException;
import pl.polsl.workflow.manager.server.model.*;
import pl.polsl.workflow.manager.server.view.UserPost;
import pl.polsl.workflow.manager.server.view.UserView;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User map(UserPost userPost) {
        User user;
        switch (Role.valueOf(userPost.getRole())) {
            case MANAGER:
                user = new Manager();
                break;
            case COORDINATOR:
                user = new Coordinator();
                break;
            case WORKER:
                user = new Worker();
                break;
            default:
                throw new NotFoundException(userPost.getRole());
        }
        user.setUsername(userPost.getUsername());
        return user;
    }

    @Override
    public UserView map(User user) {
        UserView userView = new UserView();
        userView.setRoleCode(user.getRole().name());
        userView.setId(user.getId());
        userView.setUsername(user.getUsername());
        return userView;
    }
}
