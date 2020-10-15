package pl.polsl.workflow.manager.server.mapper;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.model.Group;
import pl.polsl.workflow.manager.server.view.GroupPatch;
import pl.polsl.workflow.manager.server.view.GroupPost;
import pl.polsl.workflow.manager.server.view.GroupView;

public interface GroupMapper {

    @NonNull
    GroupView map(@NonNull Group group);

    @NonNull
    Group map(@NonNull GroupPost groupPost);

    void map(@NonNull GroupPatch groupPatch, @NonNull Group group);

}
