package pl.polsl.workflow.manager.server.mapper;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.view.TaskPost;
import pl.polsl.workflow.manager.server.view.TaskView;
import pl.polsl.workflow.manager.server.view.TaskWorkerReportPost;

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
        taskView.setIsSubTask(task.getIsSubtask());
        taskView.setAssignDate(task.getAssignDate());
        taskView.setCreateDate(task.getCreateDate());
        taskView.setGroupId(task.getGroup().getId());
        taskView.setStartDate(task.getStartDate());
        taskView.setDeadline(task.getDeadline());
        taskView.setDescription(task.getDescription());
        taskView.setSharedTaskId(task.getSharedTaskId());
        taskView.setName(task.getName());
        taskView.setLocalizationId(task.getLocalization().getId());
        taskView.setEstimatedExecutionTimeInMillis(task.getEstimatedExecutionTimeInMillis());
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
        task.setDeadline(taskPost.getDeadline());
        task.setName(taskPost.getName());
        task.setDescription(taskPost.getDescription());
        task.setEstimatedExecutionTimeInMillis(taskPost.getEstimatedExecutionTimeInMillis());
        return task;
    }

    @Override
    public void map(TaskWorkerReportPost taskWorkerReportPost, Task task) {
        task.setWorkerReport(taskWorkerReportMapper.map(taskWorkerReportPost));
    }
}
