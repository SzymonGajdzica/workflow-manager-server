package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@ToString
public class WorkerPatch {

    @ApiModelProperty(position = 1)
    @Setter(AccessLevel.NONE)
    @Nullable
    private Long groupId;

    @ApiModelProperty(hidden = true)
    private Boolean hasGroupId = false;

    public void setGroupId(@Nullable Long groupId) {
        hasGroupId = true;
        this.groupId = groupId;
    }

}
