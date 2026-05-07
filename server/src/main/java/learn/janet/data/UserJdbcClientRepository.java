package learn.janet.data;

import learn.janet.models.User;
import org.springframework.jdbc.core.simple.JdbcClient;

public class UserJdbcClientRepository implements UserRepository {
    private final JdbcClient jdbcClient;

    public UserJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public User create(User user) throws DataAccessException {
        return null;
    }
}
