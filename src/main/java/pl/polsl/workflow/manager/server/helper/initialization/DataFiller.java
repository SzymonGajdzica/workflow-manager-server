package pl.polsl.workflow.manager.server.helper.initialization;

import java.util.HashSet;
import java.util.Set;

public interface DataFiller {

    void fillDatabase();

    default Set<DataFiller> getDependencies(Set<DataFiller> dataFillers) {
        return new HashSet<>();
    }

}
