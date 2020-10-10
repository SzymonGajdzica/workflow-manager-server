package pl.polsl.workflow.manager.server.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Table(name = "localizations")
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
public class Localization extends IdEntity {

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "latitude", nullable = false)
    @NonNull
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    @NonNull
    private Double longitude;

    @Column(name = "radius", nullable = false)
    @NonNull
    private Double radius;

    @OneToMany(mappedBy = "localization")
    @NonNull
    private List<Task> tasks = new LinkedList<>();

}
