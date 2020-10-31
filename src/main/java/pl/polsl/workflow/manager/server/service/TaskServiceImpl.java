package pl.polsl.workflow.manager.server.service;

import org.springframework.stereotype.Service;
import pl.polsl.workflow.manager.server.exception.BadRequestException;
import pl.polsl.workflow.manager.server.exception.ForbiddenAccessException;
import pl.polsl.workflow.manager.server.exception.NotFoundException;
import pl.polsl.workflow.manager.server.helper.authentication.AuthenticationHelper;
import pl.polsl.workflow.manager.server.mapper.TaskMapper;
import pl.polsl.workflow.manager.server.model.*;
import pl.polsl.workflow.manager.server.repository.*;
import pl.polsl.workflow.manager.server.view.TaskPost;
import pl.polsl.workflow.manager.server.view.TaskView;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AuthenticationHelper authenticationHelper;
    private final TaskMapper taskMapper;
    private final LocalizationRepository localizationRepository;
    private final WorkerRepository workerRepository;
    private final GroupRepository groupRepository;
    private final TaskManagerReportRepository taskManagerReportRepository;

    public TaskServiceImpl(TaskRepository taskRepository, AuthenticationHelper authenticationHelper, TaskMapper taskMapper, LocalizationRepository localizationRepository, WorkerRepository workerRepository, GroupRepository groupRepository, TaskManagerReportRepository taskManagerReportRepository) {
        this.taskRepository = taskRepository;
        this.authenticationHelper = authenticationHelper;
        this.taskMapper = taskMapper;
        this.localizationRepository = localizationRepository;
        this.workerRepository = workerRepository;
        this.groupRepository = groupRepository;
        this.taskManagerReportRepository = taskManagerReportRepository;
    }

    @Override
    public TaskView createTask(TaskPost taskPost, String managerToken) {
        Task task = taskMapper.map(taskPost);
        Manager manager = authenticationHelper.getUserFromToken(managerToken);
        Group group = groupRepository.getById(taskPost.getGroupId());
        if(!manager.getGroups().contains(group))
            throw new ForbiddenAccessException("Cannot create Task for group that is not assigned to caller");
        task.setGroup(group);
        task.setLocalization(localizationRepository.getById(taskPost.getLocalizationId()));
        if (taskPost.getWorkerId() != null) {
            Worker worker = workerRepository.getById(taskPost.getWorkerId());
            if(!group.equals(worker.getGroup()))
                throw new ForbiddenAccessException("Cannot assign worker from another group to this task");
            task.setAssignedWorker(worker);
            task.setAssignDate(Instant.now());
        }
        TaskView result;
        if(taskPost.getSubTaskId() != null) {
            Task subTask = taskRepository.getById(taskPost.getSubTaskId());
            if (subTask.getManagerReport() == null)
                throw new BadRequestException("Sub task id points at task that is not finished");
            if(subTask.getManagerReport().getFixTask() != null)
                throw new BadRequestException("Task already has a fix task");
            subTask.getManagerReport().setFixTask(task);
            task.setIsSubtask(true);
            task.setSharedTaskId(subTask.getSharedTaskId());
            result = taskMapper.map(taskRepository.save(task));
            taskRepository.save(subTask);
        } else {
            task.setSharedTaskId(UUID.randomUUID());
            result = taskMapper.map(taskRepository.save(task));
        }
        return result;
    }

    @Override
    public TaskView getNextTask(String workerToken, Boolean autoStart) {
        Worker worker = authenticationHelper.getUserFromToken(workerToken);
        Task task = taskRepository.getNextTasks(worker).stream().findFirst().orElseThrow(() -> new NotFoundException("Not found next task"));
        if(autoStart != null && autoStart) {
            if(taskRepository.getActiveTasks(worker).size() != 0)
                throw new BadRequestException("Cannot start new task when has not finished all tasks");
            task.setAssignedWorker(worker);
            task.setAssignDate(Instant.now());
            task.setStartDate(Instant.now());
            return taskMapper.map(taskRepository.save(task));
        } else
            return taskMapper.map(task);
    }

    @Override
    public TaskView getCurrentTask(String workerToken) {
        Worker worker = authenticationHelper.getUserFromToken(workerToken);
        Task task = taskRepository.getActiveTasks(worker).stream().findFirst().orElseThrow(() -> new NotFoundException("No current task"));
        return taskMapper.map(task);
    }

    @Override
    public List<TaskView> getFinishedTasks(String workerToken) {
        Worker worker = authenticationHelper.getUserFromToken(workerToken);
        return taskRepository.getFinishedTasks(worker).stream().map(taskMapper::map).collect(Collectors.toList());
    }

    @Override
    public List<TaskView> getGroupTasks(Long groupId, String managerToken) {
        Group group = groupRepository.getById(groupId);
        Manager manager = authenticationHelper.getUserFromToken(managerToken);
        if(!manager.getGroups().contains(group))
            throw new ForbiddenAccessException("Cannot access group tasks not assigned to manager");
        return group.getTasks().stream().map(taskMapper::map).collect(Collectors.toList());
    }

    @Override
    public void removeTask(Long taskId) {
        Task task = taskRepository.getById(taskId);
        if(task.getStatus() != TaskStatus.CREATED)
            throw new BadRequestException("Can only delete created task");
        if(task.getIsSubtask()) {
            TaskManagerReport managerReport = taskManagerReportRepository.getByFixTask(task);
            managerReport.setFixTask(null);
            taskManagerReportRepository.save(managerReport);
        }
        taskRepository.delete(task);
    }
}
