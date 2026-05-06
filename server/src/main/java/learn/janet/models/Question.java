package learn.janet.models;

import java.util.Objects;

public class Question {
    private int id;
    private String text;
    private boolean isEdited;
    private int userId;

    public Question(int id, String text, boolean isEdited, int userId) {
        this.id = id;
        this.text = text;
        this.isEdited = isEdited;
        this.userId = userId;
    }

    public Question() {

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

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
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
        Question question = (Question) o;
        return getId() == question.getId() && isEdited() == question.isEdited() && getUserId() == question.getUserId() && Objects.equals(getText(), question.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getText(), isEdited(), getUserId());
    }
}
