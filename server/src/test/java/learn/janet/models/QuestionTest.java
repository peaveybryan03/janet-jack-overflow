package learn.janet.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void emptyQuestionShouldFailValidation() {
        Question question = new Question();

        Set<ConstraintViolation<Question>> violations = validator.validate(question);

        assertFalse(violations.isEmpty());
    }

    @Test
    void validQuestionShouldPassValidation() {
        Question question = makeValidQuestion();

        Set<ConstraintViolation<Question>> violations = validator.validate(question);

        assertTrue(violations.isEmpty());
    }

    Question makeValidQuestion() {
        Question question = new Question();
        question.setId(1);
        question.setText("Text");
        question.setEdited(false);
        question.setUserId(1);
        return question;
    }

}