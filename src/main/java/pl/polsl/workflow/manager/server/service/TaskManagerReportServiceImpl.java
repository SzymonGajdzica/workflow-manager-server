package pl.polsl.workflow.manager.server.service;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.exception.BadRequestException;
import pl.polsl.workflow.manager.server.exception.ForbiddenAccessException;
import pl.polsl.workflow.manager.server.helper.authentication.AuthenticationHelper;
import pl.polsl.workflow.manager.server.mapper.TaskManagerReportMapper;
import pl.polsl.workflow.manager.server.model.Manager;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.model.TaskManagerReport;
import pl.polsl.workflow.manager.server.model.TaskStatus;
import pl.polsl.workflow.manager.server.repository.TaskManagerReportRepository;
import pl.polsl.workflow.manager.server.repository.TaskRepository;
import pl.polsl.workflow.manager.server.view.TaskManagerReportPost;
import pl.polsl.workflow.manager.server.view.TaskManagerReportView;

@Component
public class TaskManagerReportServiceImpl implements TaskManagerReportService {

    private final TaskManagerReportRepository taskManagerReportRepository;
    private final TaskManagerReportMapper taskManagerReportMapper;
    private final AuthenticationHelper authenticationHelper;
    private final TaskRepository taskRepository;

    public TaskManagerReportServiceImpl(TaskManagerReportRepository taskManagerReportRepository, TaskManagerReportMapper taskManagerReportMapper, AuthenticationHelper authenticationHelper, TaskRepository taskRepository) {
        this.taskManagerReportRepository = taskManagerReportRepository;
        this.taskManagerReportMapper = taskManagerReportMapper;
        this.authenticationHelper = authenticationHelper;
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskManagerReportView createTaskManagerReport(TaskManagerReportPost taskManagerReportPost, String managerToken) {
        Task task = taskRepository.getById(taskManagerReportPost.getTaskId());
        Manager manager = authenticationHelper.getUserFromToken(managerToken);
        if(task.getStatus() != TaskStatus.FINISHED)
            throw new BadRequestException("Manager report can only be send for finished task");
        if(!manager.getGroups().contains(task.getGroup()))
            throw new ForbiddenAccessException("Report can only be send by group leader");
        TaskManagerReport taskManagerReport = taskManagerReportMapper.map(taskManagerReportPost);
        taskManagerReport.setTask(task);
        return taskManagerReportMapper.map(taskManagerReportRepository.save(taskManagerReport));
    }

}
