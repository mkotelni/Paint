package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class LineTool extends SizeableTool{
    private Point2D firstPoint;
    private Point2D secondPoint;

    private Shape shape;

    /**
     * LineTool constructor that will be used to draw lines
     */
    public LineTool() {shape = null;};

    /**
     * LineTool constructor that will be used to draw stars
     *
     * @param shape A star shape
     */
    public LineTool(Shape shape) {this.shape = shape;}

    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker)
    {
        canvas.setOnMouseClicked(event -> {
            super.install(canvas, graphics, colorPicker);

            if (firstPoint == null) //if it's the first click
                firstPoint = new Point2D(event.getX(), event.getY());

            else
            {
                secondPoint = new Point2D(event.getX(), event.getY());
                graphics.strokeLine(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY());

                //reset to first click again, unless we're using the star shape
                if (shape != Shape.STAR)
                    firstPoint = null;
            }
        });
    }
}
