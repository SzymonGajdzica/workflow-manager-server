package pl.polsl.workflow.manager.server.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
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
    private String name;

    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    @ManyToOne(optional = false)
    @NonNull
    private Manager creator;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private TaskStatus status;

    @ManyToOne(optional = false)
    @NonNull
    private Localization localization;

    @Column(name = "auto_assign", nullable = false)
    @NonNull
    private Boolean autoAssign;

    @ManyToOne()
    @Nullable
    private Worker assignedWorker;

    @OneToOne()
    @Nullable
    private TaskWorkerReport workerReport;

    @OneToOne()
    @Nullable
    private TaskManagerReport managerReport;

    @Column(name = "estimated_execution_time_in_millis", nullable = false)
    @NonNull
    private Long estimatedExecutionTimeInMillis;

    @Column(name = "deadline", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Date deadline;

    @Column(name = "create_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Date createDate = new Date();

    @Column(name = "assign_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Date assignDate;

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Date startDate;

}
