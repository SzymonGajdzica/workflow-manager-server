package pl.polsl.workflow.manager.server.service;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.view.GroupPost;
import pl.polsl.workflow.manager.server.view.GroupView;

import java.util.List;

public interface GroupService {

    @NonNull
    GroupView createGroup(@NonNull GroupPost groupPost);

    @NonNull
    GroupView getWorkerGroup(@NonNull String workerToken);

    @NonNull
    List<GroupView> getAllGroups();

    @NonNull
    List<GroupView> getManagerGroups(@NonNull String managerToken);

}
