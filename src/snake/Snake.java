package snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<Position> body;
    private Direction direction;
    private Direction nextDirection;
    private final GameBoard board;
    private final int tileSize;
    private Color headColor = Color.DARKGREEN;
    private Color bodyColor = Color.GREEN;

    public Snake(GameBoard board, int tileSize) {
        this.board = board;
        this.tileSize = tileSize;
        this.body = new ArrayList<>();
        this.direction = Direction.RIGHT;
        this.nextDirection = Direction.RIGHT;

        // Initialize snake with 3 segments
        int startX = board.getCols() / 2;
        int startY = board.getRows() / 2;

        for (int i = 0; i < 3; i++) {
            body.add(new Position(startX - i, startY));
        }
    }

    public void move() {
        direction = nextDirection;
        Position head = getHead();
        Position newHead = new Position(head.getX(), head.getY());

        switch (direction) {
            case UP:
                newHead.setY(newHead.getY() - 1);
                break;
            case DOWN:
                newHead.setY(newHead.getY() + 1);
                break;
            case LEFT:
                newHead.setX(newHead.getX() - 1);
                break;
            case RIGHT:
                newHead.setX(newHead.getX() + 1);
                break;
        }

        body.add(0, newHead);
        body.remove(body.size() - 1);
    }

    public void grow() {
        Position tail = body.get(body.size() - 1);
        body.add(new Position(tail.getX(), tail.getY()));
    }

    public void setDirection(Direction direction) {
        // Prevent 180-degree turns
        if ((this.direction == Direction.UP && direction != Direction.DOWN) ||
                (this.direction == Direction.DOWN && direction != Direction.UP) ||
                (this.direction == Direction.LEFT && direction != Direction.RIGHT) ||
                (this.direction == Direction.RIGHT && direction != Direction.LEFT)) {
            this.nextDirection = direction;
        }
    }

    public boolean checkWallCollision() {
        Position head = getHead();
        return head.getX() < 0 || head.getX() >= board.getCols() ||
                head.getY() < 0 || head.getY() >= board.getRows();
    }

    public boolean checkSelfCollision() {
        Position head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void draw(GraphicsContext gc) {
        // Draw body
        for (Position segment : body) {
            gc.setFill(bodyColor);
            gc.fillRect(
                    segment.getX() * tileSize,
                    segment.getY() * tileSize,
                    tileSize - 1,
                    tileSize - 1
            );
        }

        // Draw head
        Position head = getHead();
        gc.setFill(headColor);
        gc.fillRect(
                head.getX() * tileSize,
                head.getY() * tileSize,
                tileSize - 1,
                tileSize - 1
        );
    }

    public Position getHead() {
        return body.get(0);
    }

    public List<Position> getBody() {
        return body;
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT
}