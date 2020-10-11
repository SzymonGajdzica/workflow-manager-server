package pl.polsl.workflow.manager.server.mapper;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.model.TaskWorkerReport;
import pl.polsl.workflow.manager.server.view.TaskWorkerReportView;

public interface TaskWorkerReportMapper {

    @NonNull
    TaskWorkerReportView map(@NonNull TaskWorkerReport taskWorkerReport);

}
