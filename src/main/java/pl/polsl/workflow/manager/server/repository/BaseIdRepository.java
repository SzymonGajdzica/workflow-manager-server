package pl.polsl.workflow.manager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.workflow.manager.server.exception.NotFoundException;
import pl.polsl.workflow.manager.server.model.IdEntity;

public interface BaseIdRepository<T extends IdEntity> extends JpaRepository<T, Long> {

    default T getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(id));
    }

}
