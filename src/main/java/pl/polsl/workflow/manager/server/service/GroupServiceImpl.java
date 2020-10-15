package pl.polsl.workflow.manager.server.service;

import org.springframework.stereotype.Service;
import pl.polsl.workflow.manager.server.exception.NotFoundException;
import pl.polsl.workflow.manager.server.helper.authentication.AuthenticationHelper;
import pl.polsl.workflow.manager.server.mapper.GroupMapper;
import pl.polsl.workflow.manager.server.model.Group;
import pl.polsl.workflow.manager.server.model.Manager;
import pl.polsl.workflow.manager.server.model.User;
import pl.polsl.workflow.manager.server.model.Worker;
import pl.polsl.workflow.manager.server.repository.GroupRepository;
import pl.polsl.workflow.manager.server.repository.ManagerRepository;
import pl.polsl.workflow.manager.server.repository.WorkerRepository;
import pl.polsl.workflow.manager.server.view.GroupPatch;
import pl.polsl.workflow.manager.server.view.GroupPost;
import pl.polsl.workflow.manager.server.view.GroupView;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final AuthenticationHelper authenticationHelper;
    private final ManagerRepository managerRepository;
    private final WorkerRepository workerRepository;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper, AuthenticationHelper authenticationHelper, ManagerRepository managerRepository, WorkerRepository workerRepository) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.authenticationHelper = authenticationHelper;
        this.managerRepository = managerRepository;
        this.workerRepository = workerRepository;
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
        if (worker.getGroup() == null)
            throw new NotFoundException("No group assigned");
        return groupMapper.map(worker.getGroup());
    }

    @Override
    public List<GroupView> getGroups(String userToken) {
        User user = authenticationHelper.getUserFromToken(userToken);
        List<Group> groups;
        if(user instanceof Manager)
            groups = ((Manager) user).getGroups();
        else
            groups = groupRepository.findAll();
        return groups.stream().map(groupMapper::map).collect(Collectors.toList());
    }

    @Override
    public GroupView updateGroup(Long groupId, GroupPatch groupPatch) {
        Group group = groupRepository.getById(groupId);
        groupMapper.map(groupPatch, group);
        if (groupPatch.getHasManagerId()) {
            if (groupPatch.getManagerId() != null)
                group.setManager(managerRepository.getById(groupPatch.getManagerId()));
            else
                group.setManager(null);
        }
        if(groupPatch.getHasWorkerIds()) {
            List<Long> workerIdList = Optional.ofNullable(groupPatch.getWorkerIds()).orElse(Collections.emptyList());
            List<Worker> newWorkers = workerRepository.getAllById(workerIdList);
            List<Worker> oldWorkers = group.getWorkers();
            oldWorkers.forEach(worker -> worker.setGroup(null));
            newWorkers.forEach(worker -> worker.setGroup(group));
            HashSet<Worker> workersToSave = new HashSet<>();
            workersToSave.addAll(newWorkers);
            workersToSave.addAll(oldWorkers);
            workerRepository.saveAll(workersToSave);
            group.setWorkers(newWorkers);
        }
        return groupMapper.map(groupRepository.save(group));
    }

}
