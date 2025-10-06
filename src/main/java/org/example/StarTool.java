package org.example;

import javafx.geometry.Point2D;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;

public class StarTool extends SizeableTool{
    private final int X_POINTS = 0;
    private final int Y_POINTS = 1;

    private Point2D firstPoint;
    private int numPoints;

    private double[][] points;

    /*-----CONSTRUCTORS-----*/
    public StarTool() //default star is a 4-point one
    {
        numPoints = 4;
        points = new double[2][numPoints];
    }
    public StarTool(int numPoints)
    {
        this.numPoints = numPoints * 2;
        points = new double[2][this.numPoints];
    }

    /*-----HELPER FUNCTIONS-----*/
    //TODO: make it so that you can draw behind the first click like you do in shape
    public void install(Screen screen, ColorPicker colorPicker)
    {
        screen.getPreviewCanvas().setOnMousePressed(event -> {
            super.install(screen, colorPicker);
            if (firstPoint == null) //if it's the first click
                firstPoint = new Point2D(event.getX(), event.getY());
        });

        screen.getPreviewCanvas().setOnMouseDragged(event -> {
            screen.clearCanvas(screen.getPreviewGraphics());

            points = calculatePoints(event);
            screen.getPreviewGraphics().strokePolygon(points[X_POINTS], points[Y_POINTS], numPoints);
        });

        screen.getPreviewCanvas().setOnMouseReleased(event -> {
            screen.clearCanvas(screen.getPreviewGraphics());

            points = calculatePoints(event);
            screen.getDrawingGraphics().strokePolygon(points[X_POINTS], points[Y_POINTS], numPoints);

            firstPoint = null;
        });
    }

    public double[][] calculatePoints(MouseEvent event)
    {
        double width = Math.abs(firstPoint.getX() - event.getX());
        double height = Math.abs(firstPoint.getY() - event.getY());

        //get the center of the box made by the 2 clicks
        double centerX = firstPoint.getX() + Math.min(width, height) / 2;
        double centerY = firstPoint.getY() + Math.min(width, height) / 2;

        //select the smallest radius from the rectangle created by the drag and scale it by 0.5 for inner radius
        double outerRadius = Math.min(width, height) / 2;
        double innerRadius = outerRadius / 2;

        double[] xPoints = new double[numPoints];
        double[] yPoints = new double[numPoints];

        double angleStep = 2 * Math.PI / numPoints;

        //plot the points of the Star
        for (int i = 0; i < numPoints; i++)
        {
            double currentRadius = (i % 2 == 0) ? outerRadius : innerRadius;
            xPoints[i] =  centerX + (currentRadius * Math.cos(i * angleStep - Math.PI / 2));
            yPoints[i] =  centerY + (currentRadius * Math.sin(i * angleStep - Math.PI / 2));
        }

        return new double[][] {xPoints, yPoints};
    }
}
