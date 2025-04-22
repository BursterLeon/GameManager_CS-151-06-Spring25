package snake;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.List;

public class SnakeController {
    @FXML private Canvas gameCanvas;
    @FXML private Text scoreText;
    @FXML private Text highScoreText;
    @FXML private AnchorPane gamePane;

    private Timeline gameLoop;
    private Snake snake;
    private Food food;
    private GameBoard board;
    private HighScoreManager highScoreManager;
    private int score = 0;
    private boolean paused = false;
    private boolean gameOver = false;

    private static final int TILE_SIZE = 20;
    private static final int GAME_SPEED = 150; // milliseconds

    public void initialize() {
        highScoreManager = new HighScoreManager();
        updateHighScoreDisplay();

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        board = new GameBoard(gameCanvas.getWidth(), gameCanvas.getHeight(), TILE_SIZE);

        // Initialize snake and food
        snake = new Snake(board, TILE_SIZE);
        food = new Food(board, TILE_SIZE);
        food.spawn(snake.getBody());

        // Set up game loop
        gameLoop = new Timeline(new KeyFrame(Duration.millis(GAME_SPEED), e -> updateGame()));
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();

        // Set up keyboard controls
        gamePane.setFocusTraversable(true);
        gamePane.requestFocus();
        gamePane.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);

        updateScore();
    }

    private void updateGame() {
        if (paused || gameOver) return;

        // Move snake
        snake.move();

        // Check collisions
        if (snake.checkWallCollision() || snake.checkSelfCollision()) {
            endGame();
            return;
        }

        // Check food collision
        if (snake.getHead().equals(food.getPosition())) {
            snake.grow();
            food.spawn(snake.getBody());
            score += 10;
            updateScore();
        }

        // Redraw game
        drawGame();
    }

    private void drawGame() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // Draw board
        board.draw(gc);

        // Draw snake
        snake.draw(gc);

        // Draw food
        food.draw(gc);

        // Draw game over screen if needed
        if (gameOver) {
            drawGameOver(gc);
        } else if (paused) {
            drawPaused(gc);
        }
    }

    private void drawGameOver(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font(30));
        gc.fillText("GAME OVER",
                gameCanvas.getWidth()/2 - 80,
                gameCanvas.getHeight()/2 - 30);

        gc.setFont(javafx.scene.text.Font.font(20));
        gc.fillText("Final Score: " + score,
                gameCanvas.getWidth()/2 - 70,
                gameCanvas.getHeight()/2 + 10);

        gc.fillText("Press R to Restart",
                gameCanvas.getWidth()/2 - 80,
                gameCanvas.getHeight()/2 + 40);
    }

    private void drawPaused(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font(30));
        gc.fillText("PAUSED",
                gameCanvas.getWidth()/2 - 60,
                gameCanvas.getHeight()/2);
    }

    private void handleKeyPress(KeyEvent event) {
        if (gameOver) {
            if (event.getCode() == KeyCode.R) {
                restartGame();
            }
            return;
        }

        if (event.getCode() == KeyCode.ESCAPE) {
            togglePause();
            return;
        }

        if (paused) return;

        switch (event.getCode()) {
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            default:
                break;
        }
    }

    private void togglePause() {
        paused = !paused;
        if (paused) {
            gameLoop.pause();
        } else {
            gameLoop.play();
        }
        drawGame();
    }

    private void endGame() {
        gameOver = true;
        gameLoop.stop();

        // Save high score if it's a new record
        if (highScoreManager.getHighScores().isEmpty() ||
                score > highScoreManager.getHighScores().get(0)) {
            highScoreManager.saveHighScore(score);
            updateHighScoreDisplay();
        }

        drawGame();
    }

    private void restartGame() {
        gameOver = false;
        score = 0;
        updateScore();

        snake = new Snake(board, TILE_SIZE);
        food.spawn(snake.getBody());

        gameLoop.play();
        drawGame();
    }

    private void updateHighScoreDisplay() {
        List<Integer> scores = highScoreManager.getHighScores();
        if (scores.isEmpty()) {
            highScoreText.setText("High Score: 0");
        } else {
            highScoreText.setText("High Score: " + scores.get(0));
        }
    }

    private void updateScore() {
        scoreText.setText("Score: " + score);
    }
}