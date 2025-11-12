package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

/**
 * The ShapeTool is a generic tool class with basic information that relates to all click-and-drag shape tools apart from line tool
 */
public abstract class ShapeTool extends SizeableTool{
    private Point2D firstPoint;
    private ClickBox clickBox;

    /**
     * The method that specifies how the shape is drawin
     *
     * @param graphics the GraphicsContext associated with the layer to be drawn to
     */
    public abstract void drawShape(GraphicsContext graphics);

    public ShapeTool() {clickBox = new ClickBox();}

    /**
     * Returns the clickbox created by the user
     *
     * @return the clickbox
     */
    public ClickBox getClickBox() {
        return clickBox;
    }

    /**
     * Configures basic information related to all click/drag tools
     *
     * @param screen Canvas holder
     * @param colorPicker ColorPicker object
     */
    public void install(CanvasControl screen, ColorPicker colorPicker)
    {
        screen.getPreviewCanvas().setOnMousePressed(event -> {
            super.install(screen, colorPicker);
            if (firstPoint == null) {
                firstPoint = new Point2D(event.getX(), event.getY());
                clickBox.update(firstPoint, firstPoint);
            };
        });

        screen.getPreviewCanvas().setOnMouseDragged(event -> {
            screen.clearCanvas(screen.getPreviewGraphics());
            clickBox.update(firstPoint, new Point2D(event.getX(), event.getY()));
            drawShape(screen.getPreviewGraphics());
        });

        screen.getPreviewCanvas().setOnMouseReleased(event -> {
            screen.clearCanvas(screen.getPreviewGraphics());
            clickBox.update(firstPoint, new Point2D(event.getX(), event.getY()));
            drawShape(screen.getDrawingGraphics());

            firstPoint = null;

            actionPerformed(screen);
        });
    }

    /**
     * Calculates point positions for polygon-like shapes inscribed within a circle
     *
     * @param numPoints the number of points of the shape
     * @return a 2D array with the first row consisting of the calculated x-coordinates, and the second row with the calculated y-coordinates
     */
    public double[][] calculatePolygonPoints(int numPoints)
    {
        Point2D center = getClickBox().getCenter();

        double radius = Math.min(clickBox.getWidth(), clickBox.getHeight()) / 2;

        double[] xPoints = new double[numPoints];
        double[] yPoints = new double[numPoints];

        double angleStep = 2 * Math.PI / numPoints;

        //plot the points of the Polygon
        for (int i = 0; i < numPoints; i++)
        {
            xPoints[i] =  center.getX() + (radius * Math.cos(i * angleStep - Math.PI / 2));
            yPoints[i] =  center.getY() + (radius * Math.sin(i * angleStep - Math.PI / 2));
        }

        return new double[][] {xPoints, yPoints};
    }

    /**
     * Checks if the shape is actively being drawn. In other words, checks if a click has been made
     *
     * @return true if a click has been made, false otherwise
     */
    public boolean isDrawing()
    {
        return firstPoint != null; //we are drawing if a click is made
    }
}
