package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@ToString
public class TaskView {

    @ApiModelProperty(position = 1)
    @NonNull
    private Long id;

    @ApiModelProperty(position = 2)
    @NonNull
    private UUID sharedTaskId;

    @ApiModelProperty(example = "Clean swimming pool", position = 3)
    @NonNull
    private String name;

    @ApiModelProperty(example = "You have to", position = 4)
    @NonNull
    private String description;

    @ApiModelProperty(position = 5)
    @NonNull
    private Long groupId;

    @ApiModelProperty(position = 6)
    @NonNull
    private Long localizationId;

    @ApiModelProperty(position = 7)
    @Nullable
    private Long workerId;

    @ApiModelProperty(position = 8)
    @Nullable
    private TaskWorkerReportView taskWorkerReportView;

    @ApiModelProperty(position = 9)
    @Nullable
    private TaskManagerReportView taskManagerReportView;

    @ApiModelProperty(position = 10)
    @NonNull
    private Long estimatedExecutionTimeInMillis;

    @ApiModelProperty(position = 11)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Instant deadline;

    @ApiModelProperty(position = 12)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Instant createDate = Instant.now();

    @ApiModelProperty(position = 13)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Instant assignDate;

    @ApiModelProperty(position = 14)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Instant startDate;


}
