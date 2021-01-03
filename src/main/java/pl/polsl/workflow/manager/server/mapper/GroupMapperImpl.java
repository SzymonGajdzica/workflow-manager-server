package pl.polsl.workflow.manager.server.mapper;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.Group;
import pl.polsl.workflow.manager.server.model.IdEntity;
import pl.polsl.workflow.manager.server.view.GroupPatch;
import pl.polsl.workflow.manager.server.view.GroupPost;
import pl.polsl.workflow.manager.server.view.GroupView;

import java.util.stream.Collectors;

@Component
public class GroupMapperImpl implements GroupMapper {

    @Override
    public GroupView map(Group group) {
        GroupView groupView = new GroupView();
        groupView.setId(group.getId());
        groupView.setName(group.getName());
        if (group.getManager() != null)
            groupView.setManagerId(group.getManager().getId());
        groupView.setWorkerIdList(
                group.getWorkers()
                        .stream()
                        .map(IdEntity::getId)
                        .collect(Collectors.toList())
        );
        return groupView;
    }

    @Override
    public Group map(GroupPost groupPost) {
        Group group = new Group();
        group.setName(groupPost.getName());
        return group;
    }

    @Override
    public void map(GroupPatch groupPatch, Group group) {
        if(groupPatch.getName() != null)
            group.setName(groupPatch.getName());
    }
}
