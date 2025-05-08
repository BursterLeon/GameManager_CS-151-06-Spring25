package unitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.UserAccount;
import snake.*;

import static org.junit.jupiter.api.Assertions.*;

class snaketest {
    private UserAccount userAccount;

    @BeforeEach
    void newUser() {
        userAccount = new UserAccount();
    }

    // Test: Check user existence to verify
    @Test
    void testUserExists() {
        for (int i = 0; i < 10; i++) {
            // USE addUserTest INSTEAD OF addUser
            userAccount.addUserTest("username" + i, "password" + i);
            Assertions.assertTrue(userAccount.userExists("username" + i));
        }
    }

    // Test: Validate password hashing for security
    @Test
    void testPasswordHashing() {
        for (int i = 0; i < 10; i++) {
            userAccount.addUserTest("username" + i, "password" + i);
            Assertions.assertNotEquals(
                    "password" + i,
                    userAccount.getUser("username" + i).getPassword()
            );
        }
    }

    // Test: Successful login to pay the snakegame
    @Test
    void testLoginSuccess() {
        for (int i = 0; i < 10; i++) {
            userAccount.addUserTest("username" + i, "password" + i);
            Assertions.assertTrue(
                    userAccount.testLoginValidation("username" + i, "password" + i)
            );
        }
    }

    // Test: Failed login
    @Test
    void testLoginFailure() {
        for (int i = 0; i < 10; i++) {
            userAccount.addUserTest("username" + i, "password" + i);
            Assertions.assertFalse(
                    userAccount.testLoginValidation("username" + i, "wrongpassword")
            );
        }
    }

    // Test: Highscore update when the current score exceeds it
    @Test
    void testSnakeHighscore() {
        for (int i = 0; i < 10; i++) {
            userAccount.addUserTest("username" + i, "password" + i);
            // USE testLoginValidation FOR LOGIN
            userAccount.testLoginValidation("username" + i, "password" + i);
            SnakeGameFX snakeGameFX = new SnakeGameFX(userAccount);
            Assertions.assertEquals(
                    userAccount.getCurrentLoggedInUserHighScore(),
                    snakeGameFX.getUserHighscore()
            );
        }
    }
}