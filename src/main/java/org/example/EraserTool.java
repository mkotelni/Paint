package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class EraserTool extends SizeableTool{
    double lastX, lastY;

    /**
     * Constructor for EraserTool with a default width of 1 pixel
     */
    public EraserTool() {setSize(1);}

    /**
     * Constructor for EraserTool specifying eraser width
     *
     * @param size Eraser size
     */
    public EraserTool(double size) {setSize(size);}

    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker)
    {
        canvas.setOnMousePressed(event -> {
            lastX = event.getX();
            lastY = event.getY();

            graphics.setStroke(Color.valueOf("f4f4f4"));
            graphics.setLineWidth(getSize());
        });

        canvas.setOnMouseDragged(event -> {
            double x = event.getX();
            double y = event.getY();

            graphics.strokeLine(lastX, lastY, x, y);

            lastX = x;
            lastY = y;
        });
    }
}
