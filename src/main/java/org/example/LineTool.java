package org.example;

import javafx.geometry.Point2D;
import javafx.scene.control.ColorPicker;

public class LineTool extends SizeableTool{
    private Point2D firstPoint;
    private Point2D secondPoint;

    private double startX, startY;

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

    public void install(Screen screen, ColorPicker colorPicker)
    {
        screen.getPreviewCanvas().toFront(); //TODO: make previewCanvas the default canvas on front

        if (shape == Shape.STAR) //special star variant of LineTool
            drawStar(screen, colorPicker);
        else
        {
            screen.getPreviewCanvas().setOnMousePressed(event -> {
                startX = event.getX();
                startY = event.getY();
            });

            screen.getPreviewCanvas().setOnMouseDragged(event -> {
                screen.clearCanvas(screen.getPreviewGraphics());
                screen.getPreviewGraphics().strokeLine(startX, startY, event.getX(), event.getY());
            });

            screen.getPreviewCanvas().setOnMouseReleased(event -> {
                screen.getDrawingGraphics().strokeLine(startX, startY, event.getX(), event.getY());
                screen.clearCanvas(screen.getPreviewGraphics());

                screen.getDrawingCanvas().toFront();
            });
        }

    }

    //TODO: figure out if star needs its own class
    public void drawStar(Screen screen, ColorPicker colorPicker)
    {
        screen.getDrawingCanvas().setOnMouseClicked(event -> {
            super.install(screen, colorPicker);

            if (firstPoint == null) //if it's the first click
                firstPoint = new Point2D(event.getX(), event.getY());

            else
            {
                secondPoint = new Point2D(event.getX(), event.getY());
                screen.getDrawingGraphics().strokeLine(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY());

                //reset to first click again, unless we're using the star shape
                if (shape != Shape.STAR)
                    firstPoint = null;
            }
        });
    }
}
