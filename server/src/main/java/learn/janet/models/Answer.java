package learn.janet.models;

import java.util.Objects;

public class Answer {
    private int id;
    private String text;
    private int questionId;
    private int userId;

    public Answer(int id, String text, int questionId, int userId) {
        this.id = id;
        this.text = text;
        this.questionId = questionId;
        this.userId = userId;
    }

    public Answer() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return getId() == answer.getId() && getQuestionId() == answer.getQuestionId() && getUserId() == answer.getUserId() && Objects.equals(getText(), answer.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getText(), getQuestionId(), getUserId());
    }
}
