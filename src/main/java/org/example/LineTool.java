package org.example;

import javafx.scene.control.ColorPicker;

public class LineTool extends SizeableTool{
    private double startX, startY;

    public void install(CanvasControl screen, ColorPicker colorPicker)
    {
        screen.getPreviewCanvas().setOnMousePressed(event -> {
            super.install(screen, colorPicker);
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
        });
    }
}
