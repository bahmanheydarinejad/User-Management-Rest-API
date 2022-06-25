package ir.usermanagement.rest.resources;

import ir.usermanagement.mappers.AppMapper;
import ir.usermanagement.models.services.AppUserServices;
import ir.usermanagement.rest.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UsersResources extends AppBaseResources {

    private final AppUserServices userServices;

    private final AppMapper appMapper;

    @PostMapping
    public ResponseEntity<AppBaseResponse> create(@RequestBody CreateUserRequest request) {
        return ok(appMapper.toCreateUserResponse(userServices.create(appMapper.toCreateAppUserRequestDTO(request))));
    }

    @GetMapping("/{username:^[a-zA-Z0-9_.-]*$}")
    public ResponseEntity<GetUserResponse> read(@PathVariable String username) {
        return ok(appMapper.toGetUserResponse(userServices.read(username)));
    }

    @PutMapping("/{username:^[a-zA-Z0-9_.-]*$}")
    public ResponseEntity<GetUserResponse> update(@PathVariable String username, @RequestBody UpdateUserRequest request) {
        return ok(appMapper.toGetUserResponse(userServices.update(appMapper.toUpdateAppUserRequestDTO(username, request))));
    }

    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AppBaseResponse> deleteById(@PathVariable Long id) {
        userServices.deleteById(id);
        return ok(new AppBaseResponse());
    }

    @DeleteMapping("/{username:^[a-zA-Z0-9_.-]*$}")
    public ResponseEntity<AppBaseResponse> deleteByUserName(@PathVariable String username) {
        userServices.deleteByUserName(username);
        return ok(new AppBaseResponse());
    }

    @GetMapping
    public ResponseEntity<ListUserResponse> list(Pageable pageable) {
        return ok(appMapper.toListUserResponse(userServices.list(pageable)));
    }

    @PutMapping("/{username:^[a-zA-Z0-9_.-]*$}/passwords/change")
    public ResponseEntity<AppBaseResponse> changePassword(@PathVariable String username, @RequestBody ChangeUserPasswordRequest request) {
        userServices.changePassword(appMapper.toChangeAppUserPasswordRequestDTO(username, request));
        return ok(new AppBaseResponse());
    }
}
