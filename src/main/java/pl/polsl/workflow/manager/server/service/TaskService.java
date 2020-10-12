package pl.polsl.workflow.manager.server.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.workflow.manager.server.view.TaskPost;
import pl.polsl.workflow.manager.server.view.TaskView;

public interface TaskService {

    @Transactional
    @NonNull
    TaskView createTask(@NonNull TaskPost taskPost, @NonNull String userToken);

    void removeTask(@NonNull Long taskId);

    @NonNull
    TaskView getNextTask(@NonNull String userToken, @Nullable Boolean autoStart);

    @NonNull
    TaskView getCurrentTask(@NonNull String userToken);

}
