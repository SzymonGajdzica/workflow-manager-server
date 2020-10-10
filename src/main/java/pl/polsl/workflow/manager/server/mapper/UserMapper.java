package pl.polsl.workflow.manager.server.mapper;

import pl.polsl.workflow.manager.server.model.User;
import pl.polsl.workflow.manager.server.view.UserPatch;
import pl.polsl.workflow.manager.server.view.UserPost;
import pl.polsl.workflow.manager.server.view.UserView;

public interface UserMapper {

    User map(UserPost userPost);

    void map(UserPatch userPatch, User user);

    UserView map(User user);



}
