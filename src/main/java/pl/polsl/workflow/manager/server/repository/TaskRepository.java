package pl.polsl.workflow.manager.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.model.Worker;

import java.util.List;

@RepositoryRestResource
public interface TaskRepository extends BaseIdRepository<Task> {

    @Query("SELECT t FROM Task t WHERE t.assignedWorker = ?1 AND t.workerReport IS NULL ORDER BY t.deadline ASC")
    List<Task> getCurrentTasks(Worker worker);

    @Query("SELECT t FROM Task t WHERE (t.assignedWorker = ?1 OR (t.autoAssign = true AND t.assignedWorker IS NULL)) AND t.startDate IS NULL ORDER BY t.deadline ASC")
    List<Task> getNextTasks(Worker worker);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedWorker = ?1 AND t.workerReport IS NULL")
    Long getNumberOfActiveTasks(Worker worker);

}
