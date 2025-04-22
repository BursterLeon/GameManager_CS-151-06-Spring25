package snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.Random;

public class Food {
    private Position position;
    private final GameBoard board;
    private final int tileSize;
    private Color color = Color.RED;
    private Random random = new Random();

    public Food(GameBoard board, int tileSize) {
        this.board = board;
        this.tileSize = tileSize;
    }

    public void spawn(List<Position> snakeBody) {
        boolean validPosition;
        int x, y;

        do {
            validPosition = true;
            x = random.nextInt(board.getCols());
            y = random.nextInt(board.getRows());

            // Check if food spawns on snake
            for (Position segment : snakeBody) {
                if (segment.getX() == x && segment.getY() == y) {
                    validPosition = false;
                    break;
                }
            }
        } while (!validPosition);

        position = new Position(x, y);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(
                position.getX() * tileSize,
                position.getY() * tileSize,
                tileSize - 1,
                tileSize - 1
        );
    }

    public Position getPosition() {
        return position;
    }
}