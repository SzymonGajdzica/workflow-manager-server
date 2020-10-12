package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LatLng {

    @ApiModelProperty(position = 1)
    @NonNull
    @Range(min = -180L, max = 180L)
    private Double latitude;

    @ApiModelProperty(position = 2)
    @NonNull
    @Range(min = -90L, max = 90L)
    private Double longitude;

}
