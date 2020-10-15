package pl.polsl.workflow.manager.server.service;

import org.springframework.stereotype.Service;
import pl.polsl.workflow.manager.server.mapper.LocalizationMapper;
import pl.polsl.workflow.manager.server.model.Localization;
import pl.polsl.workflow.manager.server.repository.LocalizationRepository;
import pl.polsl.workflow.manager.server.view.LocalizationPost;
import pl.polsl.workflow.manager.server.view.LocalizationView;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    private final LocalizationRepository localizationRepository;
    private final LocalizationMapper localizationMapper;

    public LocalizationServiceImpl(LocalizationRepository localizationRepository, LocalizationMapper localizationMapper) {
        this.localizationRepository = localizationRepository;
        this.localizationMapper = localizationMapper;
    }

    @Override
    public LocalizationView createLocalization(LocalizationPost localizationPost) {
        Localization localization = localizationMapper.map(localizationPost);
        return localizationMapper.map(localizationRepository.save(localization));
    }

    @Override
    public List<LocalizationView> getLocalizations() {
        return localizationRepository.findAll().stream().map(localizationMapper::map).collect(Collectors.toList());
    }


}
