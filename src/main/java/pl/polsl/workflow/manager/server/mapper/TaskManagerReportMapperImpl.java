package pl.polsl.workflow.manager.server.mapper;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.TaskManagerReport;
import pl.polsl.workflow.manager.server.view.TaskManagerReportView;

@Component
public class TaskManagerReportMapperImpl implements TaskManagerReportMapper {

    private final TaskMapper taskMapper;

    public TaskManagerReportMapperImpl(@Lazy  TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskManagerReportView map(TaskManagerReport taskManagerReport) {
        TaskManagerReportView taskManagerReportView = new TaskManagerReportView();
        taskManagerReportView.setDate(taskManagerReport.getDate());
        taskManagerReportView.setId(taskManagerReport.getId());
        taskManagerReportView.setDescription(taskManagerReport.getDescription());
        if(taskManagerReport.getFixTask() != null)
            taskManagerReportView.setFixTask(taskMapper.map(taskManagerReport.getFixTask()));
        return taskManagerReportView;
    }
}
