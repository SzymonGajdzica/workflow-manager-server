package pl.polsl.workflow.manager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.exception.NotFoundIdentifierException;
import pl.polsl.workflow.manager.server.model.IdEntity;

public interface BaseIdRepository<T extends IdEntity> extends JpaRepository<T, Long> {

    default T getById(@NonNull Long id) {
        return findById(id).orElseThrow(() -> new NotFoundIdentifierException(id));
    }

}
