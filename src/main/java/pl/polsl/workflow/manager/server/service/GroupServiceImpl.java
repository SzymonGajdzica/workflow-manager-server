package pl.polsl.workflow.manager.server.service;

import org.springframework.stereotype.Service;
import pl.polsl.workflow.manager.server.exception.NotFoundException;
import pl.polsl.workflow.manager.server.mapper.GroupMapper;
import pl.polsl.workflow.manager.server.model.Group;
import pl.polsl.workflow.manager.server.model.Manager;
import pl.polsl.workflow.manager.server.model.Worker;
import pl.polsl.workflow.manager.server.repository.GroupRepository;
import pl.polsl.workflow.manager.server.repository.ManagerRepository;
import pl.polsl.workflow.manager.server.service.authentication.AuthenticationHelper;
import pl.polsl.workflow.manager.server.view.GroupPost;
import pl.polsl.workflow.manager.server.view.GroupView;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final AuthenticationHelper authenticationHelper;
    private final ManagerRepository managerRepository;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper, AuthenticationHelper authenticationHelper, ManagerRepository managerRepository) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.authenticationHelper = authenticationHelper;
        this.managerRepository = managerRepository;
    }

    @Override
    public GroupView createGroup(GroupPost groupPost) {
        Group group = groupMapper.map(groupPost);
        if (groupPost.getManagerId() != null)
            group.setManager(managerRepository.getById(groupPost.getManagerId()));
        return groupMapper.map(groupRepository.save(group));
    }

    @Override
    public GroupView getWorkerGroup(String workerToken) {
        Worker worker = authenticationHelper.getUserFromToken(workerToken);
        if(worker.getGroup() == null)
            throw new NotFoundException("No group assigned");
        return groupMapper.map(worker.getGroup());
    }

    @Override
    public List<GroupView> getAllGroups() {
        return groupRepository.findAll().stream().map(groupMapper::map).collect(Collectors.toList());
    }

    @Override
    public List<GroupView> getManagerGroups(String managerToken) {
        Manager manager = authenticationHelper.getUserFromToken(managerToken);
        return manager.getGroups().stream().map(groupMapper::map).collect(Collectors.toList());
    }
}
