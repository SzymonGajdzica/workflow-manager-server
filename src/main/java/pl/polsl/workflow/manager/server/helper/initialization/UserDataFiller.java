package pl.polsl.workflow.manager.server.helper.initialization;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.*;
import pl.polsl.workflow.manager.server.repository.GroupRepository;
import pl.polsl.workflow.manager.server.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDataFiller implements DataFiller {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public UserDataFiller(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, GroupRepository groupRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public void fillDatabase() {
        if(userRepository.count() != 0L)
            return;
        List<Group> groups = groupRepository.findAll();
        Group group = groups.stream().findFirst().orElseThrow(RuntimeException::new);

        Coordinator coordinator = new Coordinator();
        coordinator.setUsername("coordinator1");
        coordinator.setPassword(bCryptPasswordEncoder.encode("coordinator1"));
        coordinator.setRole(Role.COORDINATOR);

        Manager manager = new Manager();
        manager.setUsername("manager1");
        manager.setPassword(bCryptPasswordEncoder.encode("manager1"));
        manager.setRole(Role.MANAGER);
        groups.forEach(group1 -> group1.setManager(manager));

        Worker worker = new Worker();
        worker.setUsername("worker1");
        worker.setPassword(bCryptPasswordEncoder.encode("worker1"));
        worker.setRole(Role.WORKER);
        worker.setGroup(group);

        userRepository.saveAll(Arrays.asList(coordinator, manager, worker));
        groupRepository.saveAll(groups);
    }

    @Override
    public Set<DataFiller> getDependencies(Set<DataFiller> dataFillers) {
        return dataFillers.stream().filter(dataFiller -> dataFiller instanceof GroupDataFiller).collect(Collectors.toSet());
    }
}
