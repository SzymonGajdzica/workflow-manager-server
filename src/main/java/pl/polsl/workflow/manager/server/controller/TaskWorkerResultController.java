package pl.polsl.workflow.manager.server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.workflow.manager.server.configuration.Parameters;
import pl.polsl.workflow.manager.server.service.TaskWorkerReportService;
import pl.polsl.workflow.manager.server.view.TaskWorkerReportPost;
import pl.polsl.workflow.manager.server.view.TaskWorkerReportView;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/taskWorkerReport")
public class TaskWorkerResultController {

    private final TaskWorkerReportService taskWorkerReportService;

    public TaskWorkerResultController(TaskWorkerReportService taskWorkerReportService) {
        this.taskWorkerReportService = taskWorkerReportService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskWorkerReportView createTaskWorkerReport(
            @ApiIgnore @RequestHeader(Parameters.Authorization.HEADER) String token,
            @RequestBody TaskWorkerReportPost taskWorkerReportPost
    ) {
        return taskWorkerReportService.createTaskWorkerReport(taskWorkerReportPost, token);
    }

}
