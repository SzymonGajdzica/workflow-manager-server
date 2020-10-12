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
public class GroupPost {

    @ApiModelProperty(required = true, position = 1)
    @NonNull
    private String name;

    @ApiModelProperty(required = true, position = 7)
    @Nullable
    private Long managerId;

}
