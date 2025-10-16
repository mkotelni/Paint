package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public abstract class ShapeTool extends SizeableTool{
    private Point2D firstPoint;
    private ClickBox clickBox;

    public abstract void drawShape(GraphicsContext graphics);

    public ShapeTool() {clickBox = new ClickBox();}

    public ClickBox getClickBox() {
        return clickBox;
    }

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
        });
    }

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

    public boolean isDrawing()
    {
        return firstPoint != null; //we are drawing if a click is made
    }
}
