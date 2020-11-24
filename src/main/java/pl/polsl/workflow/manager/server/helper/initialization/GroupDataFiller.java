package pl.polsl.workflow.manager.server.helper.initialization;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.Group;
import pl.polsl.workflow.manager.server.repository.GroupRepository;

import java.util.Arrays;

@Component
public class GroupDataFiller implements DataFiller {

    private final GroupRepository groupRepository;

    public GroupDataFiller(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void fillDatabase() {
        if(groupRepository.count() != 0L)
            return;
        Group group1 = new Group();
        group1.setName("Cleaners");

        Group group2 = new Group();
        group2.setName("Mechanics");

        Group group3 = new Group();
        group3.setName("Plumbers");

        groupRepository.saveAll(Arrays.asList(group1, group2, group3));
    }
}
