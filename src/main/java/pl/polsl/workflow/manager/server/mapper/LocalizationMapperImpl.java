package pl.polsl.workflow.manager.server.mapper;

import org.springframework.stereotype.Component;
import pl.polsl.workflow.manager.server.model.Localization;
import pl.polsl.workflow.manager.server.view.LatLng;
import pl.polsl.workflow.manager.server.view.LocalizationPost;
import pl.polsl.workflow.manager.server.view.LocalizationView;

@Component
public class LocalizationMapperImpl implements LocalizationMapper {

    @Override
    public LocalizationView map(Localization localization) {
        LocalizationView localizationView = new LocalizationView();
        localizationView.setLatLng(new LatLng(localization.getLatitude(), localization.getLongitude()));
        localizationView.setName(localization.getName());
        localizationView.setRadius(localization.getRadius());
        localizationView.setId(localization.getId());
        return localizationView;
    }

    @Override
    public Localization map(LocalizationPost localizationPost) {
        Localization localization = new Localization();
        localization.setLatitude(localizationPost.getLatLng().getLatitude());
        localization.setLongitude(localizationPost.getLatLng().getLongitude());
        localization.setName(localizationPost.getName());
        localization.setRadius(localizationPost.getRadius());
        return localization;
    }

}
