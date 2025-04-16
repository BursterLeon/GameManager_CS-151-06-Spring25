package user;

public class User {
    private String name;
    private String password;
    private int highScore;

    public User(String name, String password, int highScore) {
        this.name = name;
        this.password = password;
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public int getHighScore() {
        return highScore;
    }
}
