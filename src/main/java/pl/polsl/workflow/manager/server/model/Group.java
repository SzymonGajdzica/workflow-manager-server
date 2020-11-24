package pl.polsl.workflow.manager.server.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Table(name = "groups")
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
public class Group extends IdEntity {

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @ManyToOne()
    @Nullable
    private Manager manager;

    @OneToMany(mappedBy = "group")
    @NonNull
    private List<Worker> workers = new LinkedList<>();

    @OneToMany(mappedBy = "group")
    @NonNull
    private List<Task> tasks = new LinkedList<>();

}

