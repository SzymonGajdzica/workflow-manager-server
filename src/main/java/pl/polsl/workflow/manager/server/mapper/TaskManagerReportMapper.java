package pl.polsl.workflow.manager.server.mapper;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.model.TaskManagerReport;
import pl.polsl.workflow.manager.server.view.TaskManagerReportPost;
import pl.polsl.workflow.manager.server.view.TaskManagerReportView;

public interface TaskManagerReportMapper {

    @NonNull
    TaskManagerReportView map(@NonNull TaskManagerReport taskManagerReport);

    @NonNull TaskManagerReport map(@NonNull TaskManagerReportPost taskManagerReportPost);

}
