package unitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.UserAccount;

import static org.junit.Assert.assertTrue;

class userTest {
    private UserAccount userAccount;
    @BeforeEach
    void newUser () {
        userAccount = new UserAccount();
    }
    //creates a new User, puts the User into the HashMap, and checks if the userExists method returns True
    @Test
    void testUserExists() {
        for (int i=0; i<10; i++) {
            userAccount.addUserTest("username"+i, "password"+i);
            Assertions.assertTrue(userAccount.userExists("username"+i), "Test failed");
        }
    }
}
