package ir.usermanagement.rest.models;

import ir.usermanagement.models.services.dtoes.AppUserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Setter
@Getter
public class ListUserResponse extends AppBaseResponse<Page<AppUserDTO>> {

}
