package learn.janet.data;

import learn.janet.models.User;

public interface UserRepository {
    public User create(User user) throws DataAccessException;
}
