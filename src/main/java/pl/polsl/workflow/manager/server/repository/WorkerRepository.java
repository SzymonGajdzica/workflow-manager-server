package pl.polsl.workflow.manager.server.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.polsl.workflow.manager.server.model.Worker;

@RepositoryRestResource
public interface WorkerRepository extends BaseIdRepository<Worker> {

}
