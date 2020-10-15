package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@ToString
public class TaskManagerReportPost {

    @ApiModelProperty(required = true, position = 1)
    @NonNull
    private Long taskId;

    @ApiModelProperty(example = "Not quite it", position = 2)
    @NotBlank
    @NonNull
    private String description;

}