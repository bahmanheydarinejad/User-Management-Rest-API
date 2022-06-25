package ir.usermanagement.rest.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppMessageResponseObject {

    private Integer code;

    private String description;

    private String extraInfo;

}
