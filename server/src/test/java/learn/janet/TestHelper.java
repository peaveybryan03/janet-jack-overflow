package learn.janet;

import learn.janet.models.User;

import java.util.Objects;

public class TestHelper {
    public static User existingUser() {
        return new User(1, "bryanpeavey", "peaveybryan03@gmail.com", "780429354");
    }

    public static User loginUser() {
        return new User (1, "bryanpeavey", "peaveybryan03@gmail.com", "janetluvr");
    }

    public static User userToCreate() {
        return new User(0, "janetjackson", "iamjanet@hotmail.com", "iamliterallyjanet");
    }

    public static User userAfterCreate() {
        User user = userToCreate();
        user.setId(3);
        return user;
    }
}
