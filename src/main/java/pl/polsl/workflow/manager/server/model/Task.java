package pl.polsl.workflow.manager.server.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.Instant;
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

    @Column(name = "is_subtask", nullable = false)
    @NonNull
    private Boolean isSubtask = false;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    @ManyToOne(optional = false)
    @NonNull
    private Group group;

    @ManyToOne(optional = false)
    @NonNull
    private Localization localization;

    @ManyToOne()
    @Nullable
    private Worker assignedWorker;

    @OneToOne(mappedBy = "task")
    @Nullable
    private TaskWorkerReport workerReport;

    @OneToOne(mappedBy = "task")
    @Nullable
    private TaskManagerReport managerReport;

    @Column(name = "estimated_execution_time_in_millis", nullable = false)
    @NonNull
    private Long estimatedExecutionTimeInMillis;

    @Column(name = "deadline", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Instant deadline;

    @Column(name = "create_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Instant createDate = Instant.now();

    @Column(name = "assign_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Instant assignDate;

    @Column(name = "start_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Instant startDate;

    public TaskStatus getStatus() {
        if(managerReport != null)
            return TaskStatus.ACCEPTED;
        else if(workerReport != null)
            return TaskStatus.FINISHED;
        else if(startDate != null)
            return TaskStatus.STARTED;
        else
            return TaskStatus.CREATED;
    }

}
