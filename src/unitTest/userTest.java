package unitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.UserAccount;
import snake.*;

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

    //test that ensures passwords are not stored as plain text (addUserTest should hash the password), so getPassword should not return the actual "password"+i
    @Test
    void testPasswordHashing () {
        for (int i=0; i<10; i++) {
            userAccount.addUserTest("username"+i, "password"+i);
            //parameters: (expected: the value you do not expect, actual: the value you are checking)
            Assertions.assertNotEquals("password"+i,userAccount.getUser("username"+i).getPassword(), "Test failed");
        }
    }
    //Test to validate the login logic
    @Test
    void testLoginSuccess () {
        for (int i=0; i<10; i++) {
            userAccount.addUserTest("username"+i, "password"+i);
            Assertions.assertTrue(userAccount.testLoginValidation("username"+i, "password"+i), "Test failed");
        }
    }
    @Test
    void testLoginFailure () {
        for (int i=0; i<10; i++) {
            userAccount.addUserTest("username"+i, "password"+i);
            Assertions.assertFalse(userAccount.testLoginValidation("username"+i, "wrongpassword"), "Test failed");
        }
    }
    //Test that compares the highscore from the SnakeGame user with the highscore of the currentLoggedIn user in the UserAccount class
    //steps: add user to HashMap, log in user, create SnakeGameFX instance,
    // compare userAccount current logged-in user highscore with SnakeGameFX user highscore
    @Test
    void testSnakeHighscore () {
        for (int i=0; i<10; i++) {
            userAccount.addUserTest("username"+i, "password"+i);
            userAccount.testLoginValidation("username"+i, "password"+i);
            SnakeGameFX snakeGameFX = new SnakeGameFX(userAccount);
            Assertions.assertEquals(userAccount.getCurrentLoggedInUserHighScore(),snakeGameFX.getUserHighscore(), "Test failed");
        }
    }
}