package ir.usermanagement.rest.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AppBaseResponse<RESPONSE_OBJECT> {

    protected List<AppMessageResponseObject> messages;

    protected RESPONSE_OBJECT response;

}
