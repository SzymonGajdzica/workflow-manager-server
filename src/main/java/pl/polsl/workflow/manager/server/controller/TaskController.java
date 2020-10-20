package pl.polsl.workflow.manager.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.workflow.manager.server.configuration.Parameters;
import pl.polsl.workflow.manager.server.service.TaskService;
import pl.polsl.workflow.manager.server.view.TaskPost;
import pl.polsl.workflow.manager.server.view.TaskView;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskView addTask(
            @ApiIgnore @RequestHeader(Parameters.Authorization.HEADER) String token,
            @Valid @RequestBody TaskPost taskPost
    ) {
        return taskService.createTask(taskPost, token);
    }

    @GetMapping(value = "/worker", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskView> getFinishedTasks(
            @ApiIgnore @RequestHeader(Parameters.Authorization.HEADER) String token
    ) {
        return taskService.getFinishedTasks(token);
    }

    @GetMapping(value = "/manager/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskView> getManagerTasks(
            @ApiIgnore @RequestHeader(Parameters.Authorization.HEADER) String token,
            @PathVariable Long groupId
    ) {
        return taskService.getGroupTasks(groupId, token);
    }

    @GetMapping(value = "/next", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskView getNextTask(
            @ApiIgnore @RequestHeader(Parameters.Authorization.HEADER) String token,
            @RequestParam(required = false) Boolean autoStart
    ) {
        return taskService.getNextTask(token, autoStart);
    }

    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskView getCurrentTask(
            @ApiIgnore @RequestHeader(Parameters.Authorization.HEADER) String token
    ) {
        return taskService.getCurrentTask(token);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeTask(@PathVariable Long taskId) {
        taskService.removeTask(taskId);
    }


}
