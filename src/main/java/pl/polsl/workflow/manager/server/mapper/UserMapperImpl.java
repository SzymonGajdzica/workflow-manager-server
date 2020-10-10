package pl.polsl.workflow.manager.server.mapper;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.exception.NotImplementedException;
import pl.polsl.workflow.manager.server.model.*;
import pl.polsl.workflow.manager.server.view.UserPatch;
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
                throw new NotImplementedException("This role not handled yet");
        }
        user.setUsername(userPost.getUsername());
        return user;
    }

    @Override
    public void map(UserPatch userPatch, User user) {
        if(userPatch.getEnabled() != null)
            user.setEnabled(userPatch.getEnabled());
    }

    @Override
    public UserView map(User user) {
        UserView userView = new UserView();
        userView.setRole(user.getRole().name());
        userView.setId(user.getId());
        userView.setUsername(user.getUsername());
        userView.setEnabled(user.getEnabled());
        return userView;
    }
}
