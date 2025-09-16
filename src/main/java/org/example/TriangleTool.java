package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class TriangleTool extends SizeableTool{
    private Point2D firstPoint;
    private Point2D secondPoint;

    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker)
    {
        canvas.setOnMouseClicked(event -> {
            super.install(canvas, graphics, colorPicker);

            if (firstPoint == null) //if it's the first click
                firstPoint = new Point2D(event.getX(), event.getY());
            else if (secondPoint == null) //if it's second click
                secondPoint = new Point2D(event.getX(), event.getY());
            else
            {
                double[] xPoints = {firstPoint.getX(), secondPoint.getX(), event.getX()};
                double[] yPoints = {firstPoint.getY(), secondPoint.getY(), event.getY()};

                graphics.strokePolygon(xPoints, yPoints, 3); //3 corresponds to the # of points

                firstPoint = secondPoint = null; //reset to first click again
            }
        });
    }
}
