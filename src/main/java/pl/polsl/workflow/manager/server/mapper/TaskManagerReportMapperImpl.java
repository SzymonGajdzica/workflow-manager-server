package pl.polsl.workflow.manager.server.mapper;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.TaskManagerReport;
import pl.polsl.workflow.manager.server.view.TaskManagerReportPost;
import pl.polsl.workflow.manager.server.view.TaskManagerReportView;

@Component
public class TaskManagerReportMapperImpl implements TaskManagerReportMapper {

    @Override
    public TaskManagerReportView map(TaskManagerReport taskManagerReport) {
        TaskManagerReportView taskManagerReportView = new TaskManagerReportView();
        taskManagerReportView.setDate(taskManagerReport.getDate());
        taskManagerReportView.setId(taskManagerReport.getId());
        taskManagerReportView.setDescription(taskManagerReport.getDescription());
        if(taskManagerReport.getFixTask() != null)
            taskManagerReportView.setFixTaskId(taskManagerReport.getFixTask().getId());
        return taskManagerReportView;
    }

    @Override
    public TaskManagerReport map(TaskManagerReportPost taskManagerReportPost) {
        TaskManagerReport taskManagerReport = new TaskManagerReport();
        taskManagerReport.setDescription(taskManagerReportPost.getDescription());
        return taskManagerReport;
    }
}
