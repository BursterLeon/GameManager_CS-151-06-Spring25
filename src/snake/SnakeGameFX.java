package snake;

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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Random;

public class SnakeGameFX extends Application {

    final int WIDTH = 600;
    final int HEIGHT = 600;
    final int TILE_SIZE = 20;
    final int ROWS = HEIGHT / TILE_SIZE;
    final int COLS = WIDTH / TILE_SIZE;

    LinkedList<Point> snake = new LinkedList<>();
    Point food;
    Direction direction = Direction.RIGHT;
    boolean gameOver = false;

    Timeline timeline;

    Random random = new Random();

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Snake Game (JavaFX)");
        stage.show();

        // Initialize Snake
        snake.add(new Point(COLS / 2, ROWS / 2));
        placeFood();

        // Handle Input
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP && direction != Direction.DOWN) direction = Direction.UP;
            else if (e.getCode() == KeyCode.DOWN && direction != Direction.UP) direction = Direction.DOWN;
            else if (e.getCode() == KeyCode.LEFT && direction != Direction.RIGHT) direction = Direction.LEFT;
            else if (e.getCode() == KeyCode.RIGHT && direction != Direction.LEFT) direction = Direction.RIGHT;
        });

        // Game loop
        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    void run(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font(40));
            gc.fillText("Game Over", WIDTH / 2.0 - 100, HEIGHT / 2.0);
            return;
        }

        // Move snake
        Point head = snake.peekFirst();
        Point newPoint = head.move(direction);

        // Check collisions
        if (newPoint.x < 0 || newPoint.y < 0 || newPoint.x >= COLS || newPoint.y >= ROWS || snake.contains(newPoint)) {
            gameOver = true;
            return;
        }

        // Eat food
        if (newPoint.equals(food)) {
            snake.addFirst(newPoint);
            placeFood();
        } else {
            snake.addFirst(newPoint);
            snake.removeLast();
        }

        // Draw everything
        draw(gc);
    }

    void draw(GraphicsContext gc) {
        // Background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // Snake
        gc.setFill(Color.LIMEGREEN);
        for (Point p : snake) {
            gc.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }

        // Food
        gc.setFill(Color.RED);
        gc.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    void placeFood() {
        do {
            food = new Point(random.nextInt(COLS), random.nextInt(ROWS));
        } while (snake.contains(food));
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    static class Point {
        int x, y;

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

    public static void main(String[] args) {
        launch(args);
    }
}
