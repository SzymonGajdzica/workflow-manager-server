package pl.polsl.workflow.manager.server.helper.initialization;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final Set<DataFiller> dataFillers;

    public InitialDataLoader(Set<DataFiller> dataFillers) {
        this.dataFillers = dataFillers;
    }

    @Override
    public void run(String... args) {
        checkCircularDependencies();
        HashSet<DataFiller> doneFillers = new HashSet<>();
        while (doneFillers.size() != dataFillers.size()) {
            dataFillers.forEach(dataFiller -> {
                if(!doneFillers.contains(dataFiller) && doneFillers.containsAll(dataFiller.getDependencies(dataFillers))) {
                    dataFiller.fillDatabase();
                    doneFillers.add(dataFiller);
                }
            });
        }
    }

    private void checkCircularDependencies() {
        dataFillers.forEach(dataFiller -> dataFillers.forEach(dataFiller2 -> {
            if(dataFiller.getDependencies(dataFillers).contains(dataFiller2)
                    && dataFiller2.getDependencies(dataFillers).contains(dataFiller)) {
                throw new RuntimeException(dataFiller.getClass().getSimpleName() + " & " +
                        dataFiller2.getClass().getSimpleName() + " contains circular dependencies");
            }
        }));
    }

}