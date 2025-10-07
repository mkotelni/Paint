package org.example;

import javafx.geometry.Point2D;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;

public class FanTool extends SizeableTool{
    private Point2D firstPoint;

    @Override
    public void install(Screen screen, ColorPicker colorPicker)
    {
        screen.getPreviewCanvas().setOnMouseClicked(event -> {
            super.install(screen, colorPicker);

            if (firstPoint == null)                                     //first click
                firstPoint = new Point2D(event.getX(), event.getY());
            else if (event.getButton() == MouseButton.SECONDARY) {      //stop drawing at right click
                firstPoint = null;
                screen.clearCanvas(screen.getPreviewGraphics());
            }
            else {                                                      //consecutive clicks while drawing
                screen.clearCanvas(screen.getPreviewGraphics());
                screen.getDrawingGraphics().strokeLine(firstPoint.getX(), firstPoint.getY(), event.getX(), event.getY());
            }
        });

        screen.getPreviewCanvas().setOnMouseMoved(event -> {
            if (firstPoint != null) {
                screen.clearCanvas(screen.getPreviewGraphics());

                super.install(screen, colorPicker);
                screen.getPreviewGraphics().strokeLine(firstPoint.getX(), firstPoint.getY(), event.getX(), event.getY());
            }
        });
    }
}
