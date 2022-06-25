package userssoap.orm;

import org.springframework.data.repository.CrudRepository;
import userssoap.model.User;

public interface UsersRepository extends CrudRepository<User, String> {
}
