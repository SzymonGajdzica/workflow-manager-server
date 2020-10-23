package pl.polsl.workflow.manager.server.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;

@Table(name = "task_manager_reports")
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
public class TaskManagerReport extends IdEntity {

    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    @OneToOne()
    @Nullable
    private Task fixTask;

    @Column(name = "date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Instant date = Instant.now();

    @OneToOne(optional = false)
    @NonNull
    private Task task;

}
