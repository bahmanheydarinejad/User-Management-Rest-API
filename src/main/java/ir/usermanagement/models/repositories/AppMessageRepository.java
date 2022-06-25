package ir.usermanagement.models.repositories;

import ir.usermanagement.models.repositories.entities.AppMessage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppMessageRepository extends AppBaseRepository<AppMessage, Long> {

    @Cacheable
    List<AppMessage> findAll();

}
