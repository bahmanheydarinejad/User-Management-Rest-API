package ir.usermanagement.models.repositories;

import ir.usermanagement.models.repositories.entities.AppMessageDescription;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppMessageDescriptionRepository extends AppBaseRepository<AppMessageDescription, Long> {

    @Cacheable
    List<AppMessageDescription> findAll();

}
