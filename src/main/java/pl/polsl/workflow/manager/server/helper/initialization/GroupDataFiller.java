package pl.polsl.workflow.manager.server.helper.initialization;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.Group;
import pl.polsl.workflow.manager.server.repository.GroupRepository;

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
        Group group = new Group();
        group.setName("Group1");
        groupRepository.save(group);
    }
}
