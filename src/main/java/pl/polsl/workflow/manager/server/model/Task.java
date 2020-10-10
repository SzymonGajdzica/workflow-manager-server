package pl.polsl.workflow.manager.server.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Table(name = "tasks", indexes = { @Index(columnList = "shared_task_id") })
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
public class Task extends IdEntity {

    @Column(name = "shared_task_id", nullable = false)
    @NonNull
    private UUID sharedTaskId;

    @Column(name = "name", nullable = false)
    @NonNull
    @NotBlank
    private String name;

    @Column(name = "description", nullable = false)
    @NonNull
    @NotBlank
    private String description;

    @ManyToOne(optional = false)
    @NonNull
    private Manager creator;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private TaskStatus status;

    @ManyToOne()
    @Nullable
    private Worker assignedWorker;

    @ManyToOne()
    @Nullable
    private Localization localization;

    @Column(name = "auto_assign", nullable = false)
    @NonNull
    private Boolean autoAssign;

    @Column(name = "estimated_execution_time", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NonNull
    private Date estimatedExecutionTime;

    @Column(name = "deadline", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Date deadline;

    @Column(name = "create_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Date createDate = new Date();

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Date startDate;

    @Column(name = "end_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Date endDate;

    @Column(name = "accept_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Date acceptDate;

    @OneToOne()
    @Nullable
    private TaskReport report;

    @OneToOne()
    @Nullable
    private Task subTask;

    @OneToOne()
    @Nullable
    private Task superTask;

}
