package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@NoArgsConstructor
@ToString
public class TaskPost {

    @ApiModelProperty(required = true, example = "Clean swimming pool", position = 1)
    @NonNull
    @NotBlank
    private String name;

    @ApiModelProperty(required = true, example = "You have to do", position = 2)
    @NonNull
    @NotBlank
    private String description;

    @ApiModelProperty(required = true, position = 3)
    @NonNull
    private Long localizationId;

    @ApiModelProperty(required = true, position = 4)
    @NonNull
    private Long estimatedExecutionTimeInMillis;

    @ApiModelProperty(required = true, position = 5)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Instant deadline;

    @ApiModelProperty(required = true, position = 6)
    @NonNull
    private Long groupId;

    @ApiModelProperty(required = true, position = 7)
    @Nullable
    private Long workerId;

    @ApiModelProperty(required = true, position = 8)
    @Nullable
    private Long subTaskId;

}
