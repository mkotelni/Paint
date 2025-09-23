package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

import java.util.Arrays;

public class PolygonTool extends SizeableTool{

    private Point2D[] points;
    private int numPoints;

    private int count = 0;

    public PolygonTool(){numPoints = 3; points = new Point2D[numPoints - 1];} //default polygon is a triangle
    public PolygonTool(int numPoints) {this.numPoints = numPoints; points = new Point2D[numPoints - 1];}

    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker)
    {
        canvas.setOnMouseClicked(event -> {
            super.install(canvas, graphics, colorPicker);

            if (count >= numPoints - 1) //if last point (checked first so as to not go out of bounds)
            {
                double[] xPoints = new double[numPoints];
                double[] yPoints = new double[numPoints];

                for(int i = 0; i < points.length; i++) //goes all the way until the last point, which will be filled in as the current click later
                {
                    xPoints[i] = points[i].getX();
                    yPoints[i] = points[i].getY();
                }

                //fill in last point with current click's info
                xPoints[xPoints.length - 1] = event.getX();
                yPoints[yPoints.length - 1] = event.getY();


                graphics.strokePolygon(xPoints, yPoints, numPoints);

                Arrays.fill(points, null); //set points to null again
                count = 0; //start from beginning
            }

            else if (points[count] == null) //make sure that current click isn't recorded to not overwrite it
            {
                points[count] = new Point2D(event.getX(), event.getY());
                count++; //go to next click
            }
        });
    }
}
