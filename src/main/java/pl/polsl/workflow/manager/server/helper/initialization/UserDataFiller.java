package pl.polsl.workflow.manager.server.helper.initialization;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.*;
import pl.polsl.workflow.manager.server.repository.GroupRepository;
import pl.polsl.workflow.manager.server.repository.UserRepository;

import java.util.Arrays;
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
        Group group = groupRepository.findAll().stream().findFirst().orElseThrow(RuntimeException::new);

        Coordinator coordinator = new Coordinator();
        coordinator.setUsername("coordinator1");
        coordinator.setPassword(bCryptPasswordEncoder.encode("coordinator1"));
        coordinator.setRole(Role.COORDINATOR);

        Manager manager = new Manager();
        manager.setUsername("manager1");
        manager.setPassword(bCryptPasswordEncoder.encode("manager1"));
        manager.setRole(Role.MANAGER);
        group.setManager(manager);

        Worker worker1 = new Worker();
        worker1.setUsername("worker1");
        worker1.setPassword(bCryptPasswordEncoder.encode("worker1"));
        worker1.setRole(Role.WORKER);
        worker1.setGroup(group);

        Worker worker2 = new Worker();
        worker2.setUsername("worker2");
        worker2.setPassword(bCryptPasswordEncoder.encode("worker2"));
        worker2.setRole(Role.WORKER);
        worker2.setGroup(group);

        Worker worker3 = new Worker();
        worker3.setUsername("worker3");
        worker3.setPassword(bCryptPasswordEncoder.encode("worker3"));
        worker3.setRole(Role.WORKER);
        worker3.setGroup(group);

        Worker worker4 = new Worker();
        worker4.setUsername("worker4");
        worker4.setPassword(bCryptPasswordEncoder.encode("worker4"));
        worker4.setRole(Role.WORKER);
        worker4.setGroup(group);

        userRepository.saveAll(Arrays.asList(coordinator, manager, worker1, worker2, worker3, worker4));
        groupRepository.save(group);
    }

    @Override
    public Set<DataFiller> getDependencies(Set<DataFiller> dataFillers) {
        return dataFillers.stream().filter(dataFiller -> dataFiller instanceof GroupDataFiller).collect(Collectors.toSet());
    }
}
