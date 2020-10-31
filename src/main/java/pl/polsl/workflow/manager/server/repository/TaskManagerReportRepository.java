package pl.polsl.workflow.manager.server.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.model.TaskManagerReport;

@RepositoryRestResource
public interface TaskManagerReportRepository extends BaseIdRepository<TaskManagerReport> {

    TaskManagerReport getByFixTask(Task task);

}
