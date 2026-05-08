package learn.janet.data;

import learn.janet.models.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcClientRepository implements UserRepository {
    private final JdbcClient jdbcClient;

    public UserJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public User findByEmail(String email) throws DataAccessException {
        final String sql = """
                select * from users
                where users.email = :email;
                """;

        return jdbcClient.sql(sql)
                .param("email", email)
                .query(User.class)
                .optional().orElse(null);
    }

    @Override
    public User findByName(String name) throws DataAccessException {
        final String sql = """
                select * from users
                where users.name = :name;
                """;

        return jdbcClient.sql(sql)
                .param("name", name)
                .query(User.class)
                .optional().orElse(null);
    }

    @Override
    public User create(User user) throws DataAccessException {
        final String sql = """
                insert into users (name, email, password)
                values (:name, :email, :password);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("password", user.getPassword())
                .update(keyHolder, "id");

        if (rowsAffected == 0) {
            return null;
        }

        user.setId(keyHolder.getKey().intValue());

        return user;
    }
}
