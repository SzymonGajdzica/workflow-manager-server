package pl.polsl.workflow.manager.server.service;

import org.springframework.stereotype.Service;
import pl.polsl.workflow.manager.server.exception.BadRequestException;
import pl.polsl.workflow.manager.server.exception.ForbiddenAccessException;
import pl.polsl.workflow.manager.server.helper.authentication.AuthenticationHelper;
import pl.polsl.workflow.manager.server.mapper.TaskWorkerReportMapper;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.model.TaskStatus;
import pl.polsl.workflow.manager.server.model.TaskWorkerReport;
import pl.polsl.workflow.manager.server.repository.TaskRepository;
import pl.polsl.workflow.manager.server.repository.TaskWorkerReportRepository;
import pl.polsl.workflow.manager.server.view.TaskWorkerReportPost;
import pl.polsl.workflow.manager.server.view.TaskWorkerReportView;

@Service
public class TaskWorkerReportServiceImpl implements TaskWorkerReportService {

    private final TaskWorkerReportMapper taskWorkerReportMapper;
    private final TaskWorkerReportRepository taskWorkerReportRepository;
    private final TaskRepository taskRepository;
    private final AuthenticationHelper authenticationHelper;

    public TaskWorkerReportServiceImpl(TaskWorkerReportMapper taskWorkerReportMapper, TaskWorkerReportRepository taskWorkerReportRepository, TaskRepository taskRepository, AuthenticationHelper authenticationHelper) {
        this.taskWorkerReportMapper = taskWorkerReportMapper;
        this.taskWorkerReportRepository = taskWorkerReportRepository;
        this.taskRepository = taskRepository;
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public TaskWorkerReportView createTaskWorkerReport(TaskWorkerReportPost taskWorkerReportPost, String userToken) {
        TaskWorkerReport taskWorkerReport = taskWorkerReportMapper.map(taskWorkerReportPost);
        Task task = taskRepository.getById(taskWorkerReportPost.getTaskId());
        if(!authenticationHelper.getUserFromToken(userToken).equals(task.getAssignedWorker()))
            throw new ForbiddenAccessException("Cannot create report to task that is not assigned to caller");
        if(task.getStatus() != TaskStatus.STARTED)
            throw new BadRequestException("Task has to be started by worker");
        taskWorkerReport.setTask(task);
        return taskWorkerReportMapper.map(taskWorkerReportRepository.save(taskWorkerReport));
    }
}
