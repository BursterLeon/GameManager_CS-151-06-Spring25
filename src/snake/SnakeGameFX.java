package snake;

// Librries
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import user.User;
import user.UserAccount;

import java.util.LinkedList;
import java.util.Random;

public class SnakeGameFX extends Application {

    // Constants for screen and game layout
    private static final int WIDTH = 600;
    private static final int HEIGHT = 650;
    private static final int TILE_SIZE = 20;
    private static final int ROWS = (HEIGHT - 50) / TILE_SIZE;
    private static final int COLS = WIDTH / TILE_SIZE;
    private static final int INITIAL_SPEED = 150;
    private static final int SPEED_INCREMENT = 5;

    // Game state variables
    private LinkedList<Point> snake = new LinkedList<>();
    private Point food;
    private Direction direction;
    private Direction nextDirection;
    private boolean gameOver = false;
    private boolean paused = false;
    private int score = 0;

    // User score tracking
    private UserAccount userAccount;
    private int userHighscore;
    private int highScore = 0;

    // Game loop
    private int speed = INITIAL_SPEED;
    private Timeline timeline;
    private Random random = new Random();
    private GraphicsContext gc;

     // Constructor to accepts a user account for high score management.
    public SnakeGameFX(UserAccount userAccount) {
        this.userAccount = userAccount;
        userHighscore = userAccount.getCurrentLoggedInUserHighScore();
        this.highScore = userHighscore;
    }

    // Initializes and starts the JavaFX UI
    @Override
    public void start(Stage stage) {
        // Create canvas and graphics context
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Scene and stage setup
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Snake Game (JavaFX)");
        stage.setResizable(false);
        stage.show();

        // Start the game
        initializeGame();

        // Key input handling
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();

            // Handle input when game is over
            if (gameOver) {
                switch (code) {
                    case ENTER, SPACE -> initializeGame(); // Restart game
                    case ESCAPE -> {
                        if (highScore > userHighscore) {
                            userAccount.changeUserHighScore(highScore);
                        }
                        userAccount.writeToFile();
                        stage.close();
                    }
                }
                return;
            }

            // Handle direction input and pause toggle during gameplay
            switch (code) {
                case UP -> {
                    if (direction != Direction.DOWN) nextDirection = Direction.UP;
                }
                case DOWN -> {
                    if (direction != Direction.UP) nextDirection = Direction.DOWN;
                }
                case LEFT -> {
                    if (direction != Direction.RIGHT) nextDirection = Direction.LEFT;
                }
                case RIGHT -> {
                    if (direction != Direction.LEFT) nextDirection = Direction.RIGHT;
                }
                case ESCAPE -> togglePause(); // Pause/resume
            }
        });

        // Create and start game loop timeline
        timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> {
            run();   // Game logic
            draw();  // Rendering
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Save score on window close
        stage.setOnCloseRequest(event -> {
            if (highScore > userHighscore) {
                userAccount.changeUserHighScore(highScore);
            }
            userAccount.writeToFile();
            stage.close();
        });
    }


    // Initialize or restart the game state.
    private void initializeGame() {
        snake.clear();
        snake.add(new Point(COLS / 2, ROWS / 2)); // Start in center
        direction = Direction.values()[random.nextInt(4)];
        nextDirection = direction;
        score = 0;
        speed = INITIAL_SPEED;
        gameOver = false;
        paused = false;
        placeFood();

        // Reset timeline
        if (timeline != null) {
            timeline.stop();
            timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> {
                run();
                draw();
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    // Runs game logic for movement, collisions, food.
    private void run() {
        if (paused || gameOver) return;

        direction = nextDirection;
        Point head = snake.peekFirst();
        Point newPoint = head.move(direction);

        // Collision with wall or self
        if (newPoint.x < 0 || newPoint.y < 0 || newPoint.x >= COLS || newPoint.y >= ROWS ||
                snake.contains(newPoint)) {
            gameOver = true;
            return;
        }

        // Eating food
        if (newPoint.equals(food)) {
            snake.addFirst(newPoint);
            score++;
            speed = Math.max(50, speed - SPEED_INCREMENT); // Increase speed
            timeline.setRate(INITIAL_SPEED / (double) speed);
            placeFood();
        } else {
            // Move forward
            snake.addFirst(newPoint);
            snake.removeLast();
        }
    }

    // Draws the entire game frame: snake, food, scores, overlays.
    private void draw() {
        // Background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Scores
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Score: " + score, 20, 30);
        gc.fillText("High Score: " + highScore, WIDTH - 150, 30);

        // Game boundary
        gc.setStroke(Color.DARKGREEN);
        gc.setLineWidth(2);
        gc.strokeRect(0, 50, WIDTH, HEIGHT - 50);

        // Game over screen
        if (gameOver) {
            drawGameOver();
            return;
        }

        // Pause screen
        if (paused) {
            drawPauseScreen();
            return;
        }

        // Snake
        gc.setFill(Color.LIMEGREEN);
        for (Point p : snake) {
            gc.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE + 50, TILE_SIZE - 1, TILE_SIZE - 1);
        }

        // Food
        gc.setFill(Color.RED);
        gc.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE + 50, TILE_SIZE, TILE_SIZE);
    }

    // Draws game over overlay and final scores.
    private void drawGameOver() {
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 50));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("GAME OVER", WIDTH / 2, HEIGHT / 2 - 50);

        highScore = Math.max(highScore, score); // Update high score

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 30));
        gc.fillText("Final Score: " + score, WIDTH / 2, HEIGHT / 2);
        gc.fillText("High Score: " + highScore, WIDTH / 2, HEIGHT / 2 + 40);

        gc.setFont(new Font("Arial", 20));
        gc.fillText("Press ENTER or SPACE to restart, ESC to exit", WIDTH / 2, HEIGHT / 2 + 100);
    }

     // Draws paused game overlay.
    private void drawPauseScreen() {
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 50));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("PAUSED", WIDTH / 2, HEIGHT / 2);

        gc.setFont(new Font("Arial", 20));
        gc.fillText("Press ESC to resume", WIDTH / 2, HEIGHT / 2 + 50);
    }

    // Places food at a random location.
    private void placeFood() {
        do {
            food = new Point(random.nextInt(COLS), random.nextInt(ROWS));
        } while (snake.contains(food));
    }

    // Toggles game pause state and updates rendering accordingly.
    private void togglePause() {
        paused = !paused;
        if (paused) {
            timeline.pause();
            draw();
        } else {
            timeline.play();
        }
    }

    //Direction enum for snake .
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Moves a point in a direction
        Point move(Direction dir) {
            return switch (dir) {
                case UP -> new Point(x, y - 1);
                case DOWN -> new Point(x, y + 1);
                case LEFT -> new Point(x - 1, y);
                case RIGHT -> new Point(x + 1, y);
            };
        }

        // Equals override for comparison
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point other)) return false;
            return this.x == other.x && this.y == other.y;
        }

        // Hashcode override for proper use in collections
        @Override
        public int hashCode() {
            return x * 31 + y;
        }
    }
}
