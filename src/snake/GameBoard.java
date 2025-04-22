package snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameBoard {
    private final double width;
    private final double height;
    private final int tileSize;
    private int cols;
    private int rows;

    public GameBoard(double width, double height, int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.cols = (int) (width / tileSize);
        this.rows = (int) (height / tileSize);
    }

    public void draw(GraphicsContext gc) {
        // Draw background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        // Draw grid lines
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(0.5);

        // Vertical lines
        for (int x = 0; x <= cols; x++) {
            gc.strokeLine(x * tileSize, 0, x * tileSize, rows * tileSize);
        }

        // Horizontal lines
        for (int y = 0; y <= rows; y++) {
            gc.strokeLine(0, y * tileSize, cols * tileSize, y * tileSize);
        }
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
}