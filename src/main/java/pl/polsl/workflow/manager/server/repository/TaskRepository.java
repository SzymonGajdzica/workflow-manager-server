package pl.polsl.workflow.manager.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.model.Worker;

import java.util.List;

@RepositoryRestResource
public interface TaskRepository extends BaseIdRepository<Task> {

    @Query("SELECT t FROM Task t WHERE (t.assignedWorker = ?1 OR t.assignedWorker IS NULL) AND t.startDate IS NULL ORDER BY t.deadline ASC")
    List<Task> getNextTasks(Worker worker);

    @Query("SELECT t FROM Task t LEFT OUTER JOIN TaskWorkerReport twr ON twr.task.id = t.id WHERE t.assignedWorker = ?1 AND t.startDate IS NOT NULL AND twr.id IS NULL ORDER BY t.deadline ASC")
    List<Task> getActiveTasks(Worker worker);

    @Query("SELECT t FROM Task t WHERE t.assignedWorker = ?1 AND t.workerReport IS NOT NULL ORDER BY t.deadline ASC")
    List<Task> getFinishedTasks(Worker worker);

}