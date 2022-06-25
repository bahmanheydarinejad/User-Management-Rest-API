package ir.usermanagement.rest.resources;

import ir.usermanagement.exceptions.ApiException;
import ir.usermanagement.models.entities.AppUser;
import ir.usermanagement.models.repositories.AppUserRepository;
import ir.usermanagement.rest.models.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersResourcesTest {

    @BeforeAll
    void init(@Autowired AppUserRepository appUserRepository) {
        appUserRepository.save(new AppUser("bahman.heydarinejad", new BCryptPasswordEncoder().encode("123"), "Bahman", "HeydariNejad"));
    }

    @AfterAll
    void destroy(@Autowired AppUserRepository appUserRepository) {
        appUserRepository.deleteAll();
    }

    @Test
    void createUserTest(@Autowired UsersResources usersResources) {
        CreateUserRequest request = new CreateUserRequest();
        request.setFirstName("Bahman");
        request.setLastName("HeydaiNejad");
        request.setUsername("bahman.heydarinejad" + new Random().nextInt());
        request.setPassword("123");
        ResponseEntity<AppBaseResponse> response = usersResources.create(request);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getMessages()).isNull();
        Assertions.assertThat(response.getBody().getResponse()).isNotNull();

        ApiException apiException = assertThrows(ApiException.class, () -> usersResources.create(request));
        Assertions.assertThat(apiException).isNotNull();
        Assertions.assertThat(apiException.getExceptionsDetails()).isNotEmpty();
        Assertions.assertThat(apiException.getExceptionsDetails().stream().map(detail -> detail.getCode()).collect(Collectors.toList())).contains(400001);
    }

    @Test
    void readUserTest(@Autowired UsersResources usersResources) {

        ResponseEntity<GetUserResponse> response = usersResources.read("bahman.heydarinejad");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getMessages()).isNull();
        Assertions.assertThat(response.getBody().getResponse()).isNotNull();

        ApiException apiException = assertThrows(ApiException.class, () -> usersResources.read("xyz"));
        Assertions.assertThat(apiException).isNotNull();
        Assertions.assertThat(apiException.getExceptionsDetails()).isNotEmpty();
        Assertions.assertThat(apiException.getExceptionsDetails().stream().map(detail -> detail.getCode()).collect(Collectors.toList())).contains(400002);

    }

    @Test
    void updateUserTest(@Autowired UsersResources usersResources) {

        UpdateUserRequest request = new UpdateUserRequest();
        request.setLastName("Heydari");
        ResponseEntity<GetUserResponse> response = usersResources.update("bahman.heydarinejad", request);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getMessages()).isNull();
        Assertions.assertThat(response.getBody().getResponse()).isNotNull();
        Assertions.assertThat(response.getBody().getResponse().getLastName()).isEqualTo("Heydari");

        ApiException apiException = assertThrows(ApiException.class, () -> usersResources.update("xyz", request));
        Assertions.assertThat(apiException).isNotNull();
        Assertions.assertThat(apiException.getExceptionsDetails()).isNotEmpty();
        Assertions.assertThat(apiException.getExceptionsDetails().stream().map(detail -> detail.getCode()).collect(Collectors.toList())).contains(400002);


    }

    @Test
    void changeUserPasswordTest(@Autowired UsersResources usersResources) {

        ChangeUserPasswordRequest request = new ChangeUserPasswordRequest();
        request.setOldPassword("123");
        request.setNewPassword("1234");
        request.setConfirmPassword("1234");
        ResponseEntity<AppBaseResponse> response = usersResources.changePassword("bahman.heydarinejad", request);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getMessages()).isNull();
        Assertions.assertThat(response.getBody().getResponse()).isNotNull();

        ApiException apiException = assertThrows(ApiException.class, () -> usersResources.changePassword("xyz", request));
        Assertions.assertThat(apiException).isNotNull();
        Assertions.assertThat(apiException.getExceptionsDetails()).isNotEmpty();
        Assertions.assertThat(apiException.getExceptionsDetails().get(0).getCode()).isEqualTo(400002);

        request.setOldPassword("1234");

        ApiException apiException1 = assertThrows(ApiException.class, () -> usersResources.changePassword("bahman.heydarinejad", request));
        Assertions.assertThat(apiException1).isNotNull();
        Assertions.assertThat(apiException1.getExceptionsDetails()).isNotEmpty();
        Assertions.assertThat(apiException1.getExceptionsDetails().size()).isEqualTo(1);
        Assertions.assertThat(apiException1.getExceptionsDetails().stream().map(detail -> detail.getCode()).collect(Collectors.toList())).contains(400005);

        request.setOldPassword("12345");
        request.setNewPassword("12345");
        request.setConfirmPassword("123456");
        ApiException apiException2 = assertThrows(ApiException.class, () -> usersResources.changePassword("bahman.heydarinejad", request));
        Assertions.assertThat(apiException2).isNotNull();
        Assertions.assertThat(apiException2.getExceptionsDetails()).isNotEmpty();
        Assertions.assertThat(apiException2.getExceptionsDetails().size()).isEqualTo(2);
        Assertions.assertThat(apiException2.getExceptionsDetails().stream().map(detail -> detail.getCode()).collect(Collectors.toList())).contains(400003, 400004);

    }

    @Test
    void listUserTest(@Autowired UsersResources usersResources) {
        ResponseEntity<ListUserResponse> response = usersResources.list(PageRequest.of(0, 10));

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getMessages()).isNull();
        Assertions.assertThat(response.getBody().getResponse()).isNotNull();
        Assertions.assertThat(response.getBody().getResponse().getTotalElements()).isGreaterThan(0);
        Assertions.assertThat(response.getBody().getResponse().getPageable().getPageNumber()).isEqualTo(0);
        Assertions.assertThat(response.getBody().getResponse().getPageable().getPageSize()).isEqualTo(10);

    }

    @Test
    void deleteByIdUserTest(@Autowired UsersResources usersResources, @Autowired AppUserRepository appUserRepository) {

        Long userId = appUserRepository.findFirstByUsernameEqualsIgnoreCase("bahman.heydarinejad").get().getId();

        ResponseEntity<AppBaseResponse> response = usersResources.deleteById(userId);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getMessages()).isNull();
        Assertions.assertThat(response.getBody().getResponse()).isNotNull();

        ApiException apiException = assertThrows(ApiException.class, () -> usersResources.deleteById(userId + new Random().nextInt()));
        Assertions.assertThat(apiException).isNotNull();
        Assertions.assertThat(apiException.getExceptionsDetails()).isNotEmpty();
        Assertions.assertThat(apiException.getExceptionsDetails().stream().map(detail -> detail.getCode()).collect(Collectors.toList())).contains(400002);

        init(appUserRepository);
    }

    @Test
    void deleteByUsernameUserTest(@Autowired UsersResources usersResources, @Autowired AppUserRepository appUserRepository) {

        ResponseEntity<AppBaseResponse> response = usersResources.deleteByUserName("bahman.heydarinejad");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getMessages()).isNull();
        Assertions.assertThat(response.getBody().getResponse()).isNotNull();

        ApiException apiException = assertThrows(ApiException.class, () -> usersResources.deleteByUserName("bahman.heydarinejad"));
        Assertions.assertThat(apiException).isNotNull();
        Assertions.assertThat(apiException.getExceptionsDetails()).isNotEmpty();
        Assertions.assertThat(apiException.getExceptionsDetails().stream().map(detail -> detail.getCode()).collect(Collectors.toList())).contains(400002);

        init(appUserRepository);
    }

}
