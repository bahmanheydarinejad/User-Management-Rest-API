package ir.usermanagement.rest.resources;

import ir.usermanagement.rest.models.AppBaseResponse;
import org.springframework.http.ResponseEntity;

public abstract class AppBaseResources {

    <RESPONSE extends AppBaseResponse> ResponseEntity<RESPONSE> ok(RESPONSE response) {
        return ResponseEntity.ok(response);
    }

}
