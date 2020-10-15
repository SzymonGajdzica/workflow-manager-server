package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class GroupView {

    @ApiModelProperty(position = 1)
    @NonNull
    private Long id;

    @ApiModelProperty(position = 2)
    @NonNull
    private String name;

    @ApiModelProperty(position = 3)
    @Nullable
    private Long managerId;

    @ApiModelProperty(position = 4)
    @NonNull
    private List<Long> workerIdList;

}
