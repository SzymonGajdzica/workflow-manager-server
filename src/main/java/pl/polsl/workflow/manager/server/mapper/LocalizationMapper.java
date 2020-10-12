package pl.polsl.workflow.manager.server.mapper;

import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.model.Localization;
import pl.polsl.workflow.manager.server.view.LocalizationPost;
import pl.polsl.workflow.manager.server.view.LocalizationView;

public interface LocalizationMapper {

    @NonNull
    LocalizationView map(@NonNull Localization localization);

    @NonNull
    Localization map(@NonNull LocalizationPost localizationPost);

}
