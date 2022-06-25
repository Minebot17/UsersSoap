package userssoap.orm;

import org.springframework.data.repository.CrudRepository;
import userssoap.model.Role;

public interface RolesRepository extends CrudRepository<Role, Long> {
}
