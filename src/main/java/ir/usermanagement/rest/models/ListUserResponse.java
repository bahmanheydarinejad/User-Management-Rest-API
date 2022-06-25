package ir.usermanagement.rest.models;

import ir.usermanagement.models.entities.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Setter
@Getter
public class ListUserResponse extends AppBaseResponse<Page<AppUser>> {

}
