package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;
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
    private Long creatorId;

    @ApiModelProperty(example = "CREATED", position = 6)
    @NonNull
    private String status;

    @ApiModelProperty(position = 7)
    @NonNull
    private Long localizationId;

    @ApiModelProperty(position = 8)
    @NonNull
    private Boolean autoAssign;

    @ApiModelProperty(position = 9)
    @Nullable
    private Long workerId;

    @ApiModelProperty(position = 10)
    @Nullable
    private TaskWorkerReportView taskWorkerReportView;

    @ApiModelProperty(position = 11)
    @Nullable
    private TaskManagerReportView taskManagerReportView;

    @ApiModelProperty(position = 12)
    @NonNull
    private Long estimatedExecutionTimeInMillis;

    @ApiModelProperty(position = 13)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Date deadline;

    @ApiModelProperty(position = 14)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Date createDate = new Date();

    @ApiModelProperty(position = 15)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Date assignDate;

    @ApiModelProperty(position = 16)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Date startDate;


}
