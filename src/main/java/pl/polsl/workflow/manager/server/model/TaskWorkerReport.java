package pl.polsl.workflow.manager.server.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;

@Table(name = "task_worker_reports")
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
public class TaskWorkerReport extends IdEntity {

    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    @Column(name = "success", nullable = false)
    @NonNull
    private Boolean success;

    @Column(name = "date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Instant date = Instant.now();

    @OneToOne(optional = false)
    @NonNull
    private Task task;

}
