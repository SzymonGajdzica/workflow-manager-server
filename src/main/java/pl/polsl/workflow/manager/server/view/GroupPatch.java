package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.lang.Nullable;
import pl.polsl.workflow.manager.server.helper.validation.NotBlankIfExists;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class GroupPatch {

    @ApiModelProperty(position = 1)
    @NotBlankIfExists
    @Nullable
    private String name;

    @ApiModelProperty(position = 2)
    @Setter(AccessLevel.NONE)
    @Nullable
    private Long managerId;

    @ApiModelProperty(hidden = true)
    private Boolean hasManagerId = false;

    public void setManagerId(@Nullable Long managerId) {
        this.hasManagerId = true;
        this.managerId = managerId;
    }

    @ApiModelProperty(position = 3)
    @Setter(AccessLevel.NONE)
    @Nullable
    List<Long> workerIds;

    @ApiModelProperty(hidden = true)
    private Boolean hasWorkerIds = false;

    public void setWorkerIds(@Nullable List<Long> workerIds) {
        hasWorkerIds = true;
        this.workerIds = workerIds;
    }
}
