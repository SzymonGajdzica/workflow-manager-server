package pl.polsl.workflow.manager.server.helper.initialization;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.Localization;
import pl.polsl.workflow.manager.server.repository.LocalizationRepository;

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
        Localization localization = new Localization();
        localization.setName("Gliwice");
        localization.setLatitude(50.29761);
        localization.setLongitude(18.67658);
        localization.setRadius(20.0);
        localizationRepository.save(localization);
    }

}
