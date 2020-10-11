package pl.polsl.workflow.manager.server.service;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.exception.BadRequestException;
import pl.polsl.workflow.manager.server.mapper.TaskMapper;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.repository.LocalizationRepository;
import pl.polsl.workflow.manager.server.repository.TaskRepository;
import pl.polsl.workflow.manager.server.repository.WorkerRepository;
import pl.polsl.workflow.manager.server.service.authentication.AuthenticationHelper;
import pl.polsl.workflow.manager.server.view.TaskPost;
import pl.polsl.workflow.manager.server.view.TaskView;

import java.util.UUID;

@Component
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
            if (subTask.getManagerReport() == null)
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

}
