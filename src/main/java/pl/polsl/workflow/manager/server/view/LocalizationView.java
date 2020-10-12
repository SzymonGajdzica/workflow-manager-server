package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@ToString
public class LocalizationView {

    @ApiModelProperty(position = 1)
    @NonNull
    private Long id;

    @ApiModelProperty(position = 2)
    @NonNull
    private String name;

    @ApiModelProperty(position = 3)
    @NonNull
    private LatLng latLng;

    @ApiModelProperty(position = 4)
    @NonNull
    private Double radius;

}
