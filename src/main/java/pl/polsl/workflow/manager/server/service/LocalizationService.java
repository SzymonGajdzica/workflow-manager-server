package pl.polsl.workflow.manager.server.service;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.view.LocalizationPost;
import pl.polsl.workflow.manager.server.view.LocalizationView;

import java.util.List;

public interface LocalizationService {

    @NonNull
    LocalizationView createLocalization(@NonNull LocalizationPost localizationPost);

    @NonNull
    List<LocalizationView> getLocalizations();

}
