package pl.polsl.workflow.manager.server.mapper;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.view.TaskPost;
import pl.polsl.workflow.manager.server.view.TaskView;
import pl.polsl.workflow.manager.server.view.TaskWorkerReportPost;

public interface TaskMapper {

    @NonNull
    TaskView map(@NonNull Task task);

    @NonNull
    Task map(@NonNull TaskPost taskPost);

    void map(@NonNull TaskWorkerReportPost taskWorkerReportPost, @NonNull Task task);

}
