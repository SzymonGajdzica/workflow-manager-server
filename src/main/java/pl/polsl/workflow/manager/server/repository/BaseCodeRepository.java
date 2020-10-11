package pl.polsl.workflow.manager.server.repository;

//import org.springframework.data.jpa.repository.JpaRepository;
//import pl.polsl.workflow.manager.server.exception.NotFoundException;
//import pl.polsl.workflow.manager.server.model.CodeEntity;
//
//public interface BaseCodeRepository<T extends CodeEntity> extends JpaRepository<T, String> {
//
//    default T getById(@NonNull String code) {
//        return findById(code).orElseThrow(() -> new NotFoundException(code));
//    }
//
//}
