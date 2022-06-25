package ir.usermanagement.rest.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequest {

    private String firstName;

    private String lastName;

    private String username;

    private String password;


}
