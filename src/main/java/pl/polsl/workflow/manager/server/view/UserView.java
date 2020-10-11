package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@ToString
public class UserView {

    @ApiModelProperty(position = 1)
    @NonNull
    private Long id;

    @ApiModelProperty(example = "John33", position = 2)
    @NonNull
    private String username;

    @ApiModelProperty(example = "WORKER", position = 3)
    @NonNull
    private String role;

    @ApiModelProperty(position = 4)
    @NonNull
    private Boolean enabled;

}