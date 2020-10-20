package pl.polsl.workflow.manager.server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.workflow.manager.server.configuration.Parameters;
import pl.polsl.workflow.manager.server.service.TaskManagerReportService;
import pl.polsl.workflow.manager.server.view.TaskManagerReportPost;
import pl.polsl.workflow.manager.server.view.TaskManagerReportView;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/taskManagerReport")
public class TaskManagerResultController {

    private final TaskManagerReportService taskWorkerReportService;

    public TaskManagerResultController(TaskManagerReportService taskWorkerReportService) {
        this.taskWorkerReportService = taskWorkerReportService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskManagerReportView createTaskManagerReport(
            @ApiIgnore @RequestHeader(Parameters.Authorization.HEADER) String token,
            @RequestBody TaskManagerReportPost taskManagerReportPost
    ) {
        return taskWorkerReportService.createTaskManagerReport(taskManagerReportPost, token);
    }

}
