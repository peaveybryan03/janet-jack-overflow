package learn.janet.data;

import learn.janet.models.User;

public class TestDataHelper {
    public static User existingUser() {
        return new User(1, "bryanpeavey", "peaveybryan03@gmail.com", "janetluvr");
    }
}
