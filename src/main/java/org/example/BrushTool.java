package org.example;

import javafx.scene.control.ColorPicker;

public class BrushTool extends SizeableTool{

    /**
     * BrushTool constructor with a default size of 1 pixel
     */
    public BrushTool() {setSize(1);}

    /**
     * BrushTool constructor specifying the brush size
     *
     * @param size Brush size
     */
    public BrushTool(double size) {setSize(size);}

    public void install(Screen screen, ColorPicker colorPicker)
    {
        screen.getDrawingCanvas().toFront(); //brush has no live preview mode

        screen.getDrawingCanvas().setOnMousePressed(event -> {
            screen.getDrawingGraphics().beginPath();
            screen.getDrawingGraphics().moveTo(event.getX(), event.getY());

            super.install(screen, colorPicker);
        });

        screen.getDrawingCanvas().setOnMouseDragged(event -> {
            screen.getDrawingGraphics().lineTo(event.getX(), event.getY());
            screen.getDrawingGraphics().stroke();
        });
    }
}
