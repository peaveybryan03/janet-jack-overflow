package learn.janet.domain;

import learn.janet.data.UserRepository;
import learn.janet.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static learn.janet.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockitoBean
    UserRepository repository;

    @Test
    void createFailsWhenNameIsBlank() {
        User toCreate = userToCreate();
        toCreate.setName("");

        Result<User> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Name is required."));
    }

    @Test
    void createFailsWhenEmailIsBlank() {
        User toCreate = userToCreate();
        toCreate.setEmail("");

        Result<User> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Email is required."));
    }

    @Test
    void createFailsWhenEmailIsInvalid() {
        User toCreate = userToCreate();
        toCreate.setEmail("not an email");

        Result<User> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Email must be a valid email address."));
    }

    @Test
    void createFailsWhenEmailIsDuplicate() {
        /*
        when(repository.findByEmail(userToCreate().getEmail())).thenReturn(existingUser());

        Result<User> actual = service.create(userToCreate());

        assertEquals(ResultType.CONFLICT, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Email is already taken."));
        */
    }

    @Test
    void createFailsWhenPasswordIsBlank() {
        User toCreate = userToCreate();
        toCreate.setPassword("");

        Result<User> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Password is required."));
    }

    @Test
    void createHappyPath() {
        when(repository.create(any(User.class))).thenReturn(userAfterCreate());

        Result<User> actual = service.create(userToCreate());

        assertTrue(actual.isSuccess());
        assertEquals(userAfterCreate(), actual.getPayload());
    }
}