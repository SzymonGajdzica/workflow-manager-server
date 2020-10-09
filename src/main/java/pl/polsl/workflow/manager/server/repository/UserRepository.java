package pl.polsl.workflow.manager.server.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.polsl.workflow.manager.server.model.User;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends BaseIdRepository<User> {

    Optional<User> findByUsername(String userName);

}

