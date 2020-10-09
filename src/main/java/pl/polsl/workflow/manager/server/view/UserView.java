package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@ToString
public class UserView {

    @ApiModelProperty(required = true, position = 1)
    @NonNull
    private Long id;

    @ApiModelProperty(required = true, example = "John33", position = 2)
    @NonNull
    private String username;

    @ApiModelProperty(example = "ADM", position = 3)
    @Nullable
    private String roleCode;

}