package pl.polsl.workflow.manager.server.mapper;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.TaskWorkerReport;
import pl.polsl.workflow.manager.server.view.TaskWorkerReportPost;
import pl.polsl.workflow.manager.server.view.TaskWorkerReportView;

@Component
public class TaskWorkerReportMapperImpl implements TaskWorkerReportMapper {

    @Override
    public TaskWorkerReportView map(TaskWorkerReport taskWorkerReport) {
        TaskWorkerReportView taskWorkerReportView = new TaskWorkerReportView();
        taskWorkerReportView.setDate(taskWorkerReport.getDate());
        taskWorkerReportView.setId(taskWorkerReport.getId());
        taskWorkerReportView.setDescription(taskWorkerReport.getDescription());
        taskWorkerReportView.setSuccess(taskWorkerReport.getSuccess());
        return taskWorkerReportView;
    }

    @Override
    public TaskWorkerReport map(TaskWorkerReportPost taskWorkerReportPost) {
        TaskWorkerReport taskWorkerReport = new TaskWorkerReport();
        taskWorkerReport.setSuccess(taskWorkerReportPost.getSuccess());
        taskWorkerReport.setDescription(taskWorkerReportPost.getDescription());
        return taskWorkerReport;
    }
}
