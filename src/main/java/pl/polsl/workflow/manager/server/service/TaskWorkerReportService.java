package pl.polsl.workflow.manager.server.service;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.view.TaskWorkerReportPost;
import pl.polsl.workflow.manager.server.view.TaskWorkerReportView;

public interface TaskWorkerReportService {

    @NonNull
    TaskWorkerReportView createTaskWorkerReport(@NonNull TaskWorkerReportPost taskWorkerReportPost, @NonNull String userToken);

}
