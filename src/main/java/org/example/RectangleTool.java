package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class RectangleTool extends SizeableTool{ //TODO: rename class to better reflect expanded shape drawing capabilities (maybe RECS?)
    private Point2D firstPoint;
    private double width;
    private double height;

    private Shape shape;

    /**
     * Rectangle constructor with rectangle as its default shape
     */
    public RectangleTool() {shape = Shape.RECTANGLE;} //default shape is rectangle

    /**
     * Rectangle constructor that will draw a specified shape
     *
     * @param shape A rectangle, square, ellipse, or circle shape
     */
    public RectangleTool(Shape shape) {this.shape = shape;}

    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker)
    {
        canvas.setOnMouseClicked(event -> {
            super.install(canvas, graphics, colorPicker);

            if (firstPoint == null) //if it's the first click
                firstPoint = new Point2D(event.getX(), event.getY());

            else
            {
                //needed because Point2D doesn't have setters
                double[] smallestX = {firstPoint.getX()};
                double[] smallestY = {firstPoint.getY()};

                width = Math.abs(event.getX() - firstPoint.getX());
                height = Math.abs(event.getY() - firstPoint.getY());

                //TODO: make it so that smallestX/Y drops to min width/height so that it appears to be drawn from firstPoint
                //make width == length if the shape is a square/circle
                if (shape == Shape.SQUARE || shape == Shape.CIRCLE)
                {
                    width = Math.min(width, height);
                    height = Math.min(width, height);
                }

                //set tempX/Y to the origin (top-left-most point)
                if (event.getX() < firstPoint.getX())
                    smallestX[0] = event.getX();
                if(event.getY() < firstPoint.getY())
                    smallestY[0] = event.getY();

                firstPoint = new Point2D(smallestX[0], smallestY[0]); //make firstPoint the origin

                if(shape == Shape.RECTANGLE || shape == Shape.SQUARE)
                    graphics.strokeRect(firstPoint.getX(), firstPoint.getY(), width, height);
                else
                    graphics.strokeOval(firstPoint.getX(), firstPoint.getY(), width, height);

                firstPoint = null; //reset to first click again
            }
        });
    }
}
