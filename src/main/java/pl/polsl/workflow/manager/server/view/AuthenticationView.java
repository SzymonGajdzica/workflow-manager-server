package pl.polsl.workflow.manager.server.view;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.time.Instant;

@Data
@NoArgsConstructor
@ToString
public class AuthenticationView {

    @ApiModelProperty(example = "eyJhbGciOiJI...", position = 1)
    @NonNull
    private String token;

    @ApiModelProperty(position = 2)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Instant expirationDate;



}