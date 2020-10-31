package pl.polsl.workflow.manager.server.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.workflow.manager.server.view.TaskPost;
import pl.polsl.workflow.manager.server.view.TaskView;

import java.util.List;

public interface TaskService {

    @Transactional
    @NonNull
    TaskView createTask(@NonNull TaskPost taskPost, @NonNull String managerToken);

    @Transactional
    void removeTask(@NonNull Long taskId);

    @NonNull
    TaskView getNextTask(@NonNull String workerToken, @Nullable Boolean autoStart);

    @NonNull
    TaskView getCurrentTask(@NonNull String workerToken);

    @NonNull
    List<TaskView> getFinishedTasks(@NonNull String workerToken);

    @NonNull
    List<TaskView> getGroupTasks(@NonNull Long groupId, @NonNull String managerToken);
}
