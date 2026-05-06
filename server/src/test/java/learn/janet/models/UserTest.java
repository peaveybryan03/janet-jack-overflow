package learn.janet.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void emptyUserShouldFailValidation() {
        User user = new User();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidEmailShouldFailValidation() {
        User user = makeValidUser();
        user.setEmail("invalid email");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        ConstraintViolation<User> first = violations.stream().findFirst().orElse(null);
        assertEquals("Email must be a valid email address.", first.getMessage());
    }

    @Test
    void validUserShouldPassValidation() {
        User user = makeValidUser();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    User makeValidUser() {
        User user = new User();
        user.setId(1);
        user.setName("Name");
        user.setEmail("email@email.com");
        user.setPassword("Password");
        return user;
    }

}