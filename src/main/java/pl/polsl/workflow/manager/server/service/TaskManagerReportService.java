package pl.polsl.workflow.manager.server.service;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.view.TaskManagerReportPost;
import pl.polsl.workflow.manager.server.view.TaskManagerReportView;

public interface TaskManagerReportService {

    @NonNull
    TaskManagerReportView createTaskManagerReport(@NonNull TaskManagerReportPost taskManagerReportPost, @NonNull String managerToken);

}
