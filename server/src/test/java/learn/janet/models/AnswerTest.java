package learn.janet.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void emptyAnswerShouldFailValidation() {
        Answer answer = new Answer();

        Set<ConstraintViolation<Answer>> violations = validator.validate(answer);

        assertFalse(violations.isEmpty());
    }

    @Test
    void validAnswerShouldPassValidation() {
        Answer answer = makeValidAnswer();

        Set<ConstraintViolation<Answer>> violations = validator.validate(answer);

        assertTrue(violations.isEmpty());
    }

    Answer makeValidAnswer() {
        Answer answer = new Answer();
        answer.setId(1);
        answer.setText("Text");
        answer.setQuestionId(1);
        answer.setUserId(1);
        return answer;
    }

}