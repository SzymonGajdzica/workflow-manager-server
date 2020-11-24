package pl.polsl.workflow.manager.server.helper.initialization;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.Localization;
import pl.polsl.workflow.manager.server.repository.LocalizationRepository;

import java.util.Arrays;

@Component
public class LocalizationDataFiller implements DataFiller {

    private final LocalizationRepository localizationRepository;

    public LocalizationDataFiller(LocalizationRepository localizationRepository) {
        this.localizationRepository = localizationRepository;
    }

    @Override
    public void fillDatabase() {
        if(localizationRepository.count() != 0L)
            return;
        Localization localization1 = new Localization();
        localization1.setName("Slide");
        localization1.setLatitude(50.290616);
        localization1.setLongitude(18.6746043);
        localization1.setRadius(30.0);

        Localization localization2 = new Localization();
        localization2.setName("Barrel");
        localization2.setLatitude(50.2887079);
        localization2.setLongitude(18.6769465);
        localization2.setRadius(5.0);

        Localization localization3 = new Localization();
        localization3.setName("Swimming pool");
        localization3.setLatitude(50.2898527);
        localization3.setLongitude(18.6768957);
        localization3.setRadius(60.0);

        localizationRepository.saveAll(Arrays.asList(localization1, localization2, localization3));
    }

}
