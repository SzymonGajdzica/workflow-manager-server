package pl.polsl.workflow.manager.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.workflow.manager.server.configuration.Parameters;
import pl.polsl.workflow.manager.server.service.GroupService;
import pl.polsl.workflow.manager.server.view.GroupPost;
import pl.polsl.workflow.manager.server.view.GroupView;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public GroupView createGroup(
            @Valid @RequestBody GroupPost groupPost
    ) {
        return groupService.createGroup(groupPost);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupView> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping(value = "/worker", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupView getWorkerGroup(
            @ApiIgnore @RequestHeader(value = Parameters.Authorization.HEADER) String token
    ) {
        return groupService.getWorkerGroup(token);
    }

    @GetMapping(value = "/manager", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupView> getMangerGroups(
            @ApiIgnore @RequestHeader(value = Parameters.Authorization.HEADER) String token
    ) {
        return groupService.getManagerGroups(token);
    }

}
