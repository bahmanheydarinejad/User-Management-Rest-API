package ir.usermanagement.models.repositories;

import ir.usermanagement.models.repositories.entities.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends AppBaseRepository<AppUser, Long> {

    Page<AppUser> findAll(Pageable pageable);

    Optional<AppUser> findFirstByUsernameEqualsIgnoreCase(String username);

    boolean existsByUsernameEqualsIgnoreCase(String username);

    void deleteByUsername(String username);

}
