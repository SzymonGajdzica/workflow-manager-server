package pl.polsl.workflow.manager.server.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.workflow.manager.server.view.GroupPatch;
import pl.polsl.workflow.manager.server.view.GroupPost;
import pl.polsl.workflow.manager.server.view.GroupView;

import java.util.List;

public interface GroupService {

    @NonNull
    GroupView createGroup(@NonNull GroupPost groupPost);

    @NonNull
    GroupView getWorkerGroup(@NonNull String workerToken);

    @NonNull
    List<GroupView> getGroups(@NonNull String userToken);

    @Transactional
    @NonNull
    GroupView updateGroup(@NonNull Long groupId, @NonNull GroupPatch groupPatch);


}
