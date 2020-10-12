package pl.polsl.workflow.manager.server.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.polsl.workflow.manager.server.model.Manager;

@RepositoryRestResource
public interface ManagerRepository extends BaseIdRepository<Manager> {

}
