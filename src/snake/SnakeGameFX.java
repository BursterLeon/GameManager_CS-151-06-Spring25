package snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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

    private static final int WIDTH = 600;
    private static final int HEIGHT = 650;
    private static final int TILE_SIZE = 20;
    private static final int ROWS = (HEIGHT - 50) / TILE_SIZE;
    private static final int COLS = WIDTH / TILE_SIZE;
    private static final int INITIAL_SPEED = 150;
    private static final int SPEED_INCREMENT = 5;

    private LinkedList<Point> snake = new LinkedList<>();
    private Point food;
    private Direction direction;
    private Direction nextDirection;
    private boolean gameOver = false;
    private boolean paused = false;
    private int score = 0;


    private UserAccount userAccount;
    private int userHighscore;


    private int highScore = 0;
    private int speed = INITIAL_SPEED;
    private Timeline timeline;
    private Random random = new Random();
    private GraphicsContext gc;

    //constructor takes the saved highscore from the currently logged-in user
    public SnakeGameFX(UserAccount userAccount) {
        this.userAccount = userAccount;
        userHighscore = userAccount.getCurrentLoggedInUserHighScore();
        this.highScore = userAccount.getCurrentLoggedInUserHighScore();
    }

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Snake Game (JavaFX)");
        stage.setResizable(false);
        stage.show();

        initializeGame();

        scene.setOnKeyPressed(e -> {
            if (gameOver) {
                if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                    initializeGame();
                }
                return;
            }

            switch (e.getCode()) {
                case UP:
                    if (direction != Direction.DOWN) nextDirection = Direction.UP;
                    break;
                case DOWN:
                    if (direction != Direction.UP) nextDirection = Direction.DOWN;
                    break;
                case LEFT:
                    if (direction != Direction.RIGHT) nextDirection = Direction.LEFT;
                    break;
                case RIGHT:
                    if (direction != Direction.LEFT) nextDirection = Direction.RIGHT;
                    break;
                case ESCAPE:
                    togglePause();
                    break;
            }
        });

        // Corrected Timeline initialization
        timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> {
            run();
            draw();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //make sure that the current MaxHighscore is saved, when the user closes the window or program
        stage.setOnCloseRequest(event -> {
            if (highScore > userHighscore) {
                userAccount.changeUserHighScore(highScore);
            }
            userAccount.writeToFile();
            Platform.exit();
        });
    }

    private void initializeGame() {
        snake.clear();
        snake.add(new Point(COLS / 2, ROWS / 2));
        direction = Direction.values()[random.nextInt(4)];
        nextDirection = direction;
        score = 0;
        speed = INITIAL_SPEED;
        gameOver = false;
        paused = false;
        placeFood();

        if (timeline != null) {
            timeline.stop();
            timeline = new Timeline(new KeyFrame(Duration.millis(speed),e -> {
                run();
                draw();
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    private void run() {
        if (paused || gameOver) return;

        direction = nextDirection;

        Point head = snake.peekFirst();
        Point newPoint = head.move(direction);

        if (newPoint.x < 0 || newPoint.y < 0 || newPoint.x >= COLS || newPoint.y >= ROWS ||
                snake.contains(newPoint)) {
            gameOver = true;
            return;
        }

        if (newPoint.equals(food)) {
            snake.addFirst(newPoint);
            score += 1;
            speed = Math.max(50, speed - SPEED_INCREMENT);
            timeline.setRate(INITIAL_SPEED / (double)speed);
            placeFood();
        } else {
            snake.addFirst(newPoint);
            snake.removeLast();
        }
    }

    private void draw() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Score: " + score, 20, 30);
        gc.fillText("High Score: " + highScore, WIDTH - 150, 30);

        gc.setStroke(Color.DARKGREEN);
        gc.setLineWidth(2);
        gc.strokeRect(0, 50, WIDTH, HEIGHT - 50);

        if (gameOver) {
            drawGameOver();
            return;
        }

        if (paused) {
            drawPauseScreen();
            return;
        }

        gc.setFill(Color.LIMEGREEN);
        for (Point p : snake) {
            gc.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE + 50, TILE_SIZE - 1, TILE_SIZE - 1);
        }

        gc.setFill(Color.RED);
        gc.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE + 50, TILE_SIZE, TILE_SIZE);
    }

    private void drawGameOver() {
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 50));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("GAME OVER", WIDTH / 2, HEIGHT / 2 - 50);

        highScore = Math.max(highScore, score);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 30));
        gc.fillText("Final Score: " + score, WIDTH / 2, HEIGHT / 2);
        gc.fillText("High Score: " + highScore, WIDTH / 2, HEIGHT / 2 + 40);

        gc.setFont(new Font("Arial", 20));
        gc.fillText("Press ENTER or SPACE to restart", WIDTH / 2, HEIGHT / 2 + 100);
    }

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

    private void placeFood() {
        do {
            food = new Point(random.nextInt(COLS), random.nextInt(ROWS));
        } while (snake.contains(food));
    }

    private void togglePause() {
        paused = !paused;
        if (paused) {
            timeline.pause();
        } else {
            timeline.play();
        }
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point move(Direction dir) {
            return switch (dir) {
                case UP -> new Point(x, y - 1);
                case DOWN -> new Point(x, y + 1);
                case LEFT -> new Point(x - 1, y);
                case RIGHT -> new Point(x + 1, y);
            };
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Point)) return false;
            Point other = (Point) o;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public int hashCode() {
            return x * 31 + y;
        }
    }
}