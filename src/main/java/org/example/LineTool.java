package org.example;

import javafx.scene.control.ColorPicker;

/**
 *  The LineTool class is a sizeable tool used to draw straight lines
 */
public class LineTool extends SizeableTool{
    private double startX, startY;

    /**
     * Configures the line tool
     *
     * @param screen Canvas holder
     * @param colorPicker ColorPicker object
     */
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
