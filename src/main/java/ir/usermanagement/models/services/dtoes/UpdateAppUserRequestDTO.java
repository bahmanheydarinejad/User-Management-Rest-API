package ir.usermanagement.models.services.dtoes;

import lombok.Data;

@Data
public class UpdateAppUserRequestDTO {

    private String username;
    private String firstName;
    private String lastName;
}
