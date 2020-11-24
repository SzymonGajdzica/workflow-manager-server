package pl.polsl.workflow.manager.server.helper.initialization;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.Group;
import pl.polsl.workflow.manager.server.model.Localization;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.model.Worker;
import pl.polsl.workflow.manager.server.repository.GroupRepository;
import pl.polsl.workflow.manager.server.repository.LocalizationRepository;
import pl.polsl.workflow.manager.server.repository.TaskRepository;
import pl.polsl.workflow.manager.server.repository.WorkerRepository;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TaskDataFiller implements DataFiller {

    private final TaskRepository taskRepository;
    private final GroupRepository groupRepository;
    private final WorkerRepository workerRepository;
    private final LocalizationRepository localizationRepository;

    public TaskDataFiller(TaskRepository taskRepository, GroupRepository groupRepository, WorkerRepository workerRepository, LocalizationRepository localizationRepository) {
        this.taskRepository = taskRepository;
        this.groupRepository = groupRepository;
        this.workerRepository = workerRepository;
        this.localizationRepository = localizationRepository;
    }

    @Override
    public void fillDatabase() {
        if(taskRepository.count() != 0L)
            return;

        Group group = groupRepository.findAll().stream().filter(group1 -> group1.getManager() != null).findFirst().orElseThrow(RuntimeException::new);
        Worker worker = workerRepository.findAll().stream().filter(worker1 -> group.equals(worker1.getGroup())).findFirst().orElseThrow(RuntimeException::new);
        Localization localization = localizationRepository.findAll().stream().findFirst().orElseThrow(RuntimeException::new);

        Task task1 = new Task();
        task1.setGroup(group);
        task1.setAssignedWorker(worker);
        task1.setName("Clean slide");
        task1.setDescription("Use special detergents that can be found in warehouse. Do not forget about about handrails.");
        task1.setDeadline(Instant.now().plusMillis((100000L)));
        task1.setEstimatedExecutionTimeInMillis(60L * 60L * 1000L);
        task1.setSharedTaskId(UUID.randomUUID());
        task1.setLocalization(localization);
        task1.setAssignDate(Instant.now());

        Task task2 = new Task();
        task2.setGroup(group);
        task2.setAssignedWorker(null);
        task2.setName("Clean place under slide");
        task2.setDescription("Desc");
        task2.setDeadline(Instant.now().plusMillis(100000000L));
        task2.setEstimatedExecutionTimeInMillis(60L * 60L * 1000L);
        task2.setSharedTaskId(UUID.randomUUID());
        task2.setLocalization(localization);
        task2.setAssignDate(null);

        taskRepository.saveAll(Arrays.asList(task1, task2));
    }

    @Override
    public Set<DataFiller> getDependencies(Set<DataFiller> dataFillers) {
        return dataFillers
                .stream()
                .filter(dataFiller ->
                        dataFiller instanceof LocalizationDataFiller ||
                        dataFiller instanceof UserDataFiller ||
                        dataFiller instanceof GroupDataFiller)
                .collect(Collectors.toSet());
    }
}
