package ir.usermanagement.rest.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeUserPasswordRequest {

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;

}
