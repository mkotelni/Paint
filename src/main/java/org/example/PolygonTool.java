package org.example;

import javafx.scene.canvas.GraphicsContext;

/**
 * The PolygonTool class is a sizeable tool that is used to draw polygons
 */
public class PolygonTool extends ShapeTool{
    private final int X_POINTS = 0;
    private final int Y_POINTS = 1;

    private double[][] points;
    private int numPoints;

    /*-----CONSTRUCTORS-----*/
    public PolygonTool() //default polygon is a triangle, need at least 3 points
    {                    //important to note that this triangle will be offset a little, due to how it's drawn (in a circle opposed to in a square)
        numPoints = 3;
        points = new double[2][numPoints];
    }

    /**
     * Creates a polygon tool
     *
     * @param numPoints the number of points of the polygon
     */
    public PolygonTool(int numPoints)
    {
        this.numPoints = numPoints;
        points = new double[2][numPoints];
    }

    /*-----HELPER FUNCTIONS-----*/

    /**
     * Draws the polygon
     *
     * @param graphics the GraphicsContext associated with the layer to be drawn to
     */
    @Override
    public void drawShape(GraphicsContext graphics) {
        getClickBox().makeSquare();
        points = calculatePolygonPoints(numPoints);
        graphics.strokePolygon(points[X_POINTS], points[Y_POINTS], numPoints);
    }
}
