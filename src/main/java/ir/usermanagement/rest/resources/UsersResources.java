package ir.usermanagement.rest.resources;

import ir.usermanagement.exceptions.ApiException;
import ir.usermanagement.mappers.AppMapper;
import ir.usermanagement.models.entities.AppUser;
import ir.usermanagement.models.repositories.AppUserRepository;
import ir.usermanagement.rest.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UsersResources extends AppBaseResources {

    private final AppUserRepository appUserRepository;

    private final AppMapper appMapper;

    @PostMapping
    public ResponseEntity<AppBaseResponse> create(@RequestBody CreateUserRequest request) {
        if (appUserRepository.existsByUsernameEqualsIgnoreCase(request.getUsername())) {
            throw new ApiException().addException(400001);
        }

        return ok(appMapper.toCreateUserResponse(appUserRepository.save(appMapper.toCreateUser(request, new BCryptPasswordEncoder()))));
    }

    @GetMapping("/{username:^[a-zA-Z0-9_.-]*$}}")
    public ResponseEntity<GetUserResponse> read(@PathVariable String username) {
        return ok(appMapper.toGetUserResponse(appUserRepository.findFirstByUsernameEqualsIgnoreCase(username).orElseThrow(() -> new ApiException().addException(400002))));
    }

    @PutMapping("/{username:^[a-zA-Z0-9_.-]*$}}")
    public ResponseEntity<GetUserResponse> update(@PathVariable String username, @RequestBody UpdateUserRequest request) {
        return ok(appMapper.toGetUserResponse(appUserRepository.save(appMapper.toUpdateUser(appUserRepository.findFirstByUsernameEqualsIgnoreCase(username).orElseThrow(() -> new ApiException().addException(400002)), request))));
    }

    @DeleteMapping("/{id:^[0-9]+$}")
    public ResponseEntity<AppBaseResponse> deleteById(@PathVariable Long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new ApiException().addException(400002));
        appUserRepository.delete(appUser);
        return ok(appMapper.toDeleteUserResponse(appUser));
    }

    @DeleteMapping("/{username:^[a-zA-Z0-9_.-]*$}")
    public ResponseEntity<AppBaseResponse> deleteByUserName(@PathVariable String username) {
        AppUser appUser = appUserRepository.findFirstByUsernameEqualsIgnoreCase(username).orElseThrow(() -> new ApiException().addException(400002));
        appUserRepository.delete(appUser);
        return ok(appMapper.toDeleteUserResponse(appUser));
    }

    @GetMapping
    public ResponseEntity<ListUserResponse> list(Pageable pageable) {
        return ok(appMapper.toListUserResponse(appUserRepository.findAll(pageable)));
    }

    @PutMapping("/{username:^[a-zA-Z0-9_.-]*$}/passwords/change")
    public ResponseEntity<AppBaseResponse> changePassword(@PathVariable String username, @RequestBody ChangeUserPasswordRequest request) {
        ApiException apiException = new ApiException();

        AppUser appUser = appUserRepository.findFirstByUsernameEqualsIgnoreCase(username).orElseThrow(() -> apiException.addException(400002));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean matchOldWithPersist = passwordEncoder.matches(request.getOldPassword(), appUser.getPassword());
        if (!matchOldWithPersist) {
            // old password is not match
            apiException.addException(400003);
        }

        if (!request.getNewPassword().contentEquals(request.getConfirmPassword())) {
            // confirm password is not match
            apiException.addException(400004);
        }

        boolean matchNewWithPersist = passwordEncoder.matches(request.getNewPassword(), appUser.getPassword());
        if (matchOldWithPersist && matchNewWithPersist) {
            // old and new password should not be same
            apiException.addException(400005);
        }

        if (!CollectionUtils.isEmpty(apiException.getExceptionsDetails()))
            throw apiException;

        return ok(appMapper.toGetUserResponse(appUserRepository.save(appMapper.toChangeUserPassword(appUser, request, passwordEncoder))));
    }
}
