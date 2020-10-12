package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@ToString
public class LocalizationPost {

    @ApiModelProperty(position = 1)
    @NonNull
    private String name;

    @ApiModelProperty(position = 2)
    @NonNull
    private LatLng latLng;

    @ApiModelProperty(position = 3)
    @NonNull
    @Min(0L)
    private Double radius;

}
