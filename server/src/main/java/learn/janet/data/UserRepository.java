package learn.janet.data;

import learn.janet.models.User;

public interface UserRepository {
    User findByEmail(String email) throws DataAccessException;

    User create(User user) throws DataAccessException;
}
