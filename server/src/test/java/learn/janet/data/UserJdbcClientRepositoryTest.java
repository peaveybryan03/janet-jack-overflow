package learn.janet.data;

import learn.janet.TestHelper;
import learn.janet.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static learn.janet.TestHelper.existingUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcClientRepositoryTest {

    @Autowired
    private UserJdbcClientRepository repository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setup() {
        jdbcClient.sql("call set_known_good_state();").update();
    }

    @Test
    void shouldNotFindByEmailWhenDoesNotExist() {
        User actual = repository.findByEmail("notinrepo@gmail.com");

        assertNull(actual);
    }

    @Test
    void shouldFindByEmailWhenExists() {
        User actual = repository.findByEmail(existingUser().getEmail());

        assertNotNull(actual);
        assertEquals(existingUser(), actual);
    }

    @Test
    void shouldCreate() {
        User toCreate = TestHelper.userToCreate();
        User expected = TestHelper.userToCreate();
        expected.setId(3);

        User actual = repository.create(toCreate);

        assertEquals(expected, actual);
    }
}