package pl.polsl.workflow.manager.server.service;

import org.springframework.stereotype.Service;
import pl.polsl.workflow.manager.server.exception.BadRequestException;
import pl.polsl.workflow.manager.server.exception.NotFoundException;
import pl.polsl.workflow.manager.server.mapper.TaskMapper;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.model.TaskStatus;
import pl.polsl.workflow.manager.server.model.Worker;
import pl.polsl.workflow.manager.server.repository.LocalizationRepository;
import pl.polsl.workflow.manager.server.repository.TaskRepository;
import pl.polsl.workflow.manager.server.repository.WorkerRepository;
import pl.polsl.workflow.manager.server.service.authentication.AuthenticationHelper;
import pl.polsl.workflow.manager.server.view.TaskPost;
import pl.polsl.workflow.manager.server.view.TaskView;

import java.util.Date;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AuthenticationHelper authenticationHelper;
    private final TaskMapper taskMapper;
    private final LocalizationRepository localizationRepository;
    private final WorkerRepository workerRepository;

    public TaskServiceImpl(TaskRepository taskRepository, AuthenticationHelper authenticationHelper, TaskMapper taskMapper, LocalizationRepository localizationRepository, WorkerRepository workerRepository) {
        this.taskRepository = taskRepository;
        this.authenticationHelper = authenticationHelper;
        this.taskMapper = taskMapper;
        this.localizationRepository = localizationRepository;
        this.workerRepository = workerRepository;
    }

    @Override
    public TaskView createTask(TaskPost taskPost, String userToken) {
        Task task = taskMapper.map(taskPost);
        task.setCreator(authenticationHelper.getUserFromToken(userToken));
        task.setLocalization(localizationRepository.getById(taskPost.getLocalizationId()));
        if (taskPost.getWorkerId() != null) {
            task.setAssignedWorker(workerRepository.getById(taskPost.getWorkerId()));
        }
        TaskView result;
        if(taskPost.getSubTaskId() != null) {
            Task subTask = taskRepository.getById(taskPost.getSubTaskId());
            if (subTask.getManagerReport() == null || subTask.getStatus() != TaskStatus.FINISHED)
                throw new BadRequestException("Sub task id points at task that is not finished");
            subTask.getManagerReport().setFixTask(task);
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
    public TaskView getNextTask(String userToken, Boolean autoStart) {
        Worker worker = authenticationHelper.getUserFromToken(userToken);
        Task task = taskRepository.getNextTasks(worker).stream().findFirst().orElseThrow(() -> new NotFoundException("Not found next task"));
        if(autoStart != null && autoStart) {
            if(taskRepository.getNumberOfActiveTasks(worker) != 0L)
                throw new BadRequestException("Cannot start new task when has not finished all tasks");
            task.setAssignedWorker(worker);
            task.setStartDate(new Date());
            return taskMapper.map(taskRepository.save(task));
        } else
            return taskMapper.map(task);
    }

    @Override
    public TaskView getCurrentTask(String userToken) {
        Worker worker = authenticationHelper.getUserFromToken(userToken);
        Task task = taskRepository.getCurrentTasks(worker).stream().findFirst().orElseThrow(() -> new NotFoundException("No current task"));
        return taskMapper.map(task);
    }

    @Override
    public void removeTask(Long taskId) {
        Task task = taskRepository.getById(taskId);
        if(task.getStatus() != TaskStatus.CREATED)
            throw new BadRequestException("Can only delete created task");
        taskRepository.delete(task);
    }
}
