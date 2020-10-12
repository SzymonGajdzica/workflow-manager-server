package pl.polsl.workflow.manager.server.service.initialization;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.service.LocalizationServiceImpl;
import pl.polsl.workflow.manager.server.service.UserServiceImpl;

import java.util.Arrays;
import java.util.List;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final List<DataFiller> dataFillers;

    public InitialDataLoader(UserServiceImpl userService, LocalizationServiceImpl localizationService) {
        dataFillers = Arrays.asList(userService, localizationService);
    }

    @Override
    public void run(String... args) {
        dataFillers.forEach(DataFiller::fillDatabase);
    }
}