package ir.usermanagement.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AppBaseRepository<ENTITY, ID> extends CrudRepository<ENTITY, ID> {
}
