package snake;

import java.io.*;
import java.util.*;

public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "data/snake_highscores.txt";
    private List<Integer> highScores;

    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadHighScores();
    }

    private void loadHighScores() {
        try {
            File file = new File(HIGH_SCORE_FILE);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return;
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextInt()) {
                highScores.add(scanner.nextInt());
            }
            scanner.close();

            // Sort in descending order
            highScores.sort(Collections.reverseOrder());

            // Keep only top 5 scores
            if (highScores.size() > 5) {
                highScores = highScores.subList(0, 5);
            }

        } catch (IOException e) {
            System.err.println("Error loading high scores: " + e.getMessage());
        }
    }

    public void saveHighScore(int score) {
        highScores.add(score);
        highScores.sort(Collections.reverseOrder());

        // Keep only top 5 scores
        if (highScores.size() > 5) {
            highScores = highScores.subList(0, 5);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORE_FILE))) {
            for (int highScore : highScores) {
                writer.println(highScore);
            }
        } catch (IOException e) {
            System.err.println("Error saving high scores: " + e.getMessage());
        }
    }

    public List<Integer> getHighScores() {
        return new ArrayList<>(highScores);
    }
}