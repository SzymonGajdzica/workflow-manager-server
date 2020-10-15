package pl.polsl.workflow.manager.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.exception.NotFoundIdentifierException;
import pl.polsl.workflow.manager.server.model.IdEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface BaseIdRepository<T extends IdEntity> extends JpaRepository<T, Long> {

    @NonNull
    default T getById(@NonNull Long id) {
        return findById(id).orElseThrow(() -> new NotFoundIdentifierException(id));
    }

    @NonNull
    default List<T> getAllById(@NonNull Collection<Long> ids) {
        List<T> items = findAllById(ids);
        if(items.size() != ids.size()) {
            List<Long> foundIds = items.stream().map(IdEntity::getId).collect(Collectors.toList());
            throw new NotFoundIdentifierException(ids.stream().filter(id -> !foundIds.contains(id)).collect(Collectors.toList()));
        }
        return items;
    }

}
