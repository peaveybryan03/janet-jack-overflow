package learn.janet.data;

import learn.janet.models.User;

public class TestDataHelper {
    public static User existingUser() {
        return new User(1, "bryanpeavey", "peaveybryan03@gmail.com", "janetluvr");
    }

    public static User userToCreate() {
        return new User(0, "janetjackson", "iamjanet@hotmail.com", "iamliterallyjanet");
    }
}
