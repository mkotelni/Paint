package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;

import java.util.Arrays;

public class IrregularPolygonTool extends SizeableTool{
    private Point2D[] points; //TODO: consider implementing points as a list in order to not have to track count manually
    private int numPoints;

    private boolean isDrawing = false;
    private int count = 0;

    public IrregularPolygonTool()
    {
        numPoints = 3; //need at least 3 points for a polygon
        points = new Point2D[numPoints];
    }

    public IrregularPolygonTool(int numPoints)
    {
        this.numPoints = numPoints;
        points = new Point2D[numPoints];
    }

    public void install(CanvasControl screen, ColorPicker colorPicker)
    {
        screen.getPreviewCanvas().setOnMouseClicked(event -> {
            super.install(screen, colorPicker);

            if (event.getButton() == MouseButton.PRIMARY) //add points on left click
            {
                points[count] = new Point2D(event.getX(), event.getY());
                isDrawing = true;
                count++;
            }

            if (event.getButton() == MouseButton.SECONDARY) //cancel polygon on right click
            {
                screen.clearCanvas(screen.getPreviewGraphics());
                reset();
            }

            if (count == numPoints) //burn to drawingCanvas once # of points has been reached
            {
                screen.clearCanvas(screen.getPreviewGraphics());
                drawIrregularPolygon(screen.getDrawingGraphics());

                reset();
            }
        });

        screen.getPreviewCanvas().setOnMouseMoved(event -> {
            if (!isDrawing) return;

            screen.clearCanvas(screen.getPreviewGraphics());
            super.install(screen, colorPicker);

            points[count] = new Point2D(event.getX(), event.getY());
            drawIrregularPolygon(screen.getPreviewGraphics());
        });
    }

    private void drawIrregularPolygon(GraphicsContext graphics)
    {
        double[] xPoints = new double[numPoints];
        double[] yPoints = new double[numPoints];

        int maxCount = 0;
        for (int i = 0; (i < points.length) && (points[i] != null); i++) //loops to the current amount of points made
        {
            xPoints[i] = points[i].getX();
            yPoints[i] = points[i].getY();

            maxCount++;
        }

        graphics.strokePolygon(xPoints, yPoints, maxCount);
    }

    //resets to the default state
    private void reset()
    {
        Arrays.fill(points, null);
        isDrawing = false;
        count = 0;
    }
}
