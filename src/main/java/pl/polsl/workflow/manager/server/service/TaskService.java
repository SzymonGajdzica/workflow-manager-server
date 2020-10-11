package pl.polsl.workflow.manager.server.service;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.view.TaskPost;
import pl.polsl.workflow.manager.server.view.TaskView;

public interface TaskService {

    @NonNull
    TaskView createTask(TaskPost taskPost, String userToken);

}
