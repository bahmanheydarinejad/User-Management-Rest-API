package ir.usermanagement.models.services.dtoes;

import lombok.Data;

@Data
public class ChangeAppUserPasswordRequestDTO {

    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
