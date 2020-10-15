package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;
import pl.polsl.workflow.manager.server.helper.validation.ValueOfEnum;
import pl.polsl.workflow.manager.server.model.Role;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@ToString
public class UserPost {

    @ApiModelProperty(required = true, example = "John33", position = 1)
    @NonNull
    @NotBlank
    private String username;

    @ApiModelProperty(required = true, example = "4j21521j3h5gv", position = 2)
    @NonNull
    @NotBlank
    @Length(min = 3, max = 20)
    private String password;

    @ApiModelProperty(required = true, example = "WORKER", position = 3)
    @NonNull
    @ValueOfEnum(enumClass = Role.class)
    private String role;


}