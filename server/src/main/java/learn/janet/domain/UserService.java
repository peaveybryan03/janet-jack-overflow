package learn.janet.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import learn.janet.data.DataAccessException;
import learn.janet.data.UserRepository;
import learn.janet.models.User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository repository;
    private final Validator validator;

    public UserService(UserRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Result<User> create(User user) throws DataAccessException {
        Result<User> result = new Result<>();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                result.addErrorMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        /*
        if (repository.findByEmail(user.getEmail()) != null) {
            result.addErrorMessage("Email is already taken.", ResultType.CONFLICT);
        }
        */

        if (result.isSuccess()) {
            int passwordHash = Objects.hash(user.getPassword());
            String passwordHashString = String.valueOf(passwordHash);
            user.setPassword(passwordHashString);

            User created = repository.create(user);
            result.setPayload(created);
        }

        return result;
    }
}
