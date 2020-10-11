package pl.polsl.workflow.manager.server.mapper;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.model.TaskStatus;
import pl.polsl.workflow.manager.server.view.TaskPost;
import pl.polsl.workflow.manager.server.view.TaskView;

@Component
public class TaskMapperImpl implements TaskMapper {

    private final TaskManagerReportMapper taskManagerReportMapper;
    private final TaskWorkerReportMapper taskWorkerReportMapper;

    public TaskMapperImpl(TaskManagerReportMapper taskManagerReportMapper, TaskWorkerReportMapper taskWorkerReportMapper) {
        this.taskManagerReportMapper = taskManagerReportMapper;
        this.taskWorkerReportMapper = taskWorkerReportMapper;
    }

    @Override
    public TaskView map(Task task) {
        TaskView taskView = new TaskView();
        taskView.setId(task.getId());
        taskView.setAssignDate(task.getAssignDate());
        taskView.setAutoAssign(task.getAutoAssign());
        taskView.setCreateDate(task.getCreateDate());
        taskView.setCreatorId(task.getCreator().getId());
        taskView.setStartDate(task.getStartDate());
        taskView.setDeadline(task.getDeadline());
        taskView.setDescription(task.getDescription());
        taskView.setSharedTaskId(task.getSharedTaskId());
        taskView.setName(task.getName());
        taskView.setLocalizationId(task.getLocalization().getId());
        taskView.setEstimatedExecutionTimeInMillis(task.getEstimatedExecutionTimeInMillis());
        taskView.setStatus(task.getStatus().name());
        if (task.getAssignedWorker() != null)
            taskView.setWorkerId(task.getAssignedWorker().getId());
        if (task.getManagerReport() != null)
            taskView.setTaskManagerReportView(taskManagerReportMapper.map(task.getManagerReport()));
        if (task.getWorkerReport() != null)
            taskView.setTaskWorkerReportView(taskWorkerReportMapper.map(task.getWorkerReport()));
        return taskView;
    }

    @Override
    public Task map(TaskPost taskPost) {
        Task task = new Task();
        task.setAutoAssign(taskPost.getAutoAssign());
        task.setDeadline(taskPost.getDeadline());
        task.setName(taskPost.getName());
        task.setDescription(taskPost.getDescription());
        task.setEstimatedExecutionTimeInMillis(taskPost.getEstimatedExecutionTimeInMillis());
        task.setStatus(TaskStatus.CREATED);
        return task;
    }

}
