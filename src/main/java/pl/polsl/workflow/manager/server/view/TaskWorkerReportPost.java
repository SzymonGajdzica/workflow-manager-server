package pl.polsl.workflow.manager.server.view;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@ToString
public class TaskWorkerReportPost {

    @ApiModelProperty(required = true, position = 1)
    @NonNull
    private Long taskId;

    @ApiModelProperty(example = "Not quite it", position = 2)
    @NonNull
    private String description;

    @ApiModelProperty(position = 3)
    @NonNull
    private Boolean success;

}
