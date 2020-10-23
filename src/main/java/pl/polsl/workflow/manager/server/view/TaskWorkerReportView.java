package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.time.Instant;

@Data
@NoArgsConstructor
@ToString
public class TaskWorkerReportView {

    @ApiModelProperty(position = 1)
    @NonNull
    private Long id;

    @ApiModelProperty(example = "Not quite it", position = 2)
    @NonNull
    private String description;

    @ApiModelProperty(position = 3)
    @NonNull
    private Boolean success;

    @ApiModelProperty(position = 3)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Instant date;

}
