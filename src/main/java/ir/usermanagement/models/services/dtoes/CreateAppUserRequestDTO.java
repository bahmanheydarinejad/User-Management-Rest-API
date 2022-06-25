package ir.usermanagement.models.services.dtoes;

import lombok.Data;

@Data
public class CreateAppUserRequestDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String password;

}
