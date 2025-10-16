package org.example;

import javafx.scene.canvas.GraphicsContext;

public class StarTool extends ShapeTool{
    private final int X_POINTS = 0;
    private final int Y_POINTS = 1;

    private double[][] points;
    private int numPoints;

    /*-----CONSTRUCTORS-----*/
    public StarTool() //default star is a 4-point one
    {
        numPoints = 4;
        points = new double[2][numPoints * 2];
        getClickBox().makeSquare();
    }
    public StarTool(int numPoints)
    {
        this.numPoints = numPoints * 2;
        points = new double[2][this.numPoints];
        getClickBox().makeSquare();
    }

    /*-----HELPER FUNCTIONS-----*/
    @Override
    public void drawShape(GraphicsContext graphics) {
        morphToStar(numPoints);
        graphics.strokePolygon(points[X_POINTS], points[Y_POINTS], numPoints);
    }

    public void morphToStar(int numPoints)
    {
        points = super.calculatePolygonPoints(numPoints);

        for (int i = 0; i < numPoints; i++)
        {
            //offset odd points to the inner radius of (radius / 2)
            if (i % 2 != 0)
            {
                points[X_POINTS][i] = (points[X_POINTS][i] + getClickBox().getCenter().getX()) / 2;
                points[Y_POINTS][i] = (points[Y_POINTS][i] + getClickBox().getCenter().getY()) / 2;
            }
        }
    }
}
