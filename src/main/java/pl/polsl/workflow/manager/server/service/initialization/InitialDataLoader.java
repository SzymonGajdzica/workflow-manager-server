package pl.polsl.workflow.manager.server.service.initialization;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.service.UserServiceImpl;

import java.util.Arrays;
import java.util.List;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final List<DataFiller> dataFillers;

    public InitialDataLoader(UserServiceImpl userService) {
        dataFillers = Arrays.asList(userService);
    }

    @Override
    public void run(String... args) throws Exception {
        dataFillers.forEach(DataFiller::fillDatabase);
    }
}