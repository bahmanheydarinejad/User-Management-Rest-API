package ir.usermanagement.models.services.dtoes;

import lombok.Data;

@Data
public class AppUserDTO {

    protected Long id;
    protected Long createTimeStamp;
    protected Long updateTimeStamp;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

}
