package pl.polsl.workflow.manager.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.workflow.manager.server.service.LocalizationService;
import pl.polsl.workflow.manager.server.view.LocalizationPost;
import pl.polsl.workflow.manager.server.view.LocalizationView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/localization")
public class LocalizationController {

    private final LocalizationService localizationService;

    public LocalizationController(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LocalizationView createLocalization(
            @Valid @RequestBody LocalizationPost localizationPost
    ) {
        return localizationService.createLocalization(localizationPost);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocalizationView> getLocalizations() {
        return localizationService.getLocalizations();
    }

}
