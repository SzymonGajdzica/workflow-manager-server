package pl.polsl.workflow.manager.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.polsl.workflow.manager.server.model.Task;
import pl.polsl.workflow.manager.server.model.Worker;

import java.util.List;

@RepositoryRestResource
public interface TaskRepository extends BaseIdRepository<Task> {

    @Query("SELECT t FROM Task t WHERE (t.assignedWorker IS NULL OR t.assignedWorker = :worker) AND t.startDate IS NULL AND t.group = :#{#worker.group} ORDER BY t.deadline ASC")
    List<Task> getNextTasks(@Param("worker") Worker worker);

    @Query("SELECT t FROM Task t LEFT OUTER JOIN TaskWorkerReport twr ON twr.task.id = t.id WHERE t.assignedWorker = :worker AND t.startDate IS NOT NULL AND twr.id IS NULL AND t.group = :#{#worker.group} ORDER BY t.deadline ASC")
    List<Task> getActiveTasks(@Param("worker") Worker worker);

    @Query("SELECT t FROM Task t WHERE t.assignedWorker = :worker AND t.workerReport IS NOT NULL AND t.group = :#{#worker.group}")
    List<Task> getFinishedTasks(@Param("worker") Worker worker);

    @Query("SELECT t FROM Task t WHERE t.workerReport IS NULL AND t.assignedWorker in :workers")
    List<Task> getAssignedNotFinishedTasks(@Param("workers") List<Worker> workers);

}

