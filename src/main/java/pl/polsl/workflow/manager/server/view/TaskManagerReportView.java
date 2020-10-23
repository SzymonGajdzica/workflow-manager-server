package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Data
@NoArgsConstructor
@ToString
public class TaskManagerReportView {

    @ApiModelProperty(position = 1)
    @NonNull
    private Long id;

    @ApiModelProperty(example = "Not quite it", position = 2)
    @NonNull
    private String description;

    @ApiModelProperty(position = 3)
    @Nullable
    private Long fixTaskId;

    @ApiModelProperty(position = 4)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Instant date;

}