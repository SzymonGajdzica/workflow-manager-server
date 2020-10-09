package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class AuthenticationView {

    @ApiModelProperty(required = true, example = "eyJhbGciOiJI...", position = 1)
    @NonNull
    private String token;

    @ApiModelProperty(required = true, position = 2)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Date expirationDate;



}