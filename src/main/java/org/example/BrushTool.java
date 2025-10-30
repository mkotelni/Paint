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

    /**
     * Configures the brush tool
     *
     * @param screen Canvas holder
     * @param colorPicker ColorPicker object
     */
    public void install(CanvasControl screen, ColorPicker colorPicker)
    {
        screen.getDrawingCanvas().toFront(); //brush has no live preview mode

        screen.getDrawingCanvas().setOnMousePressed(event -> {
            super.install(screen, colorPicker);

            screen.getDrawingGraphics().beginPath();
            screen.getDrawingGraphics().moveTo(event.getX(), event.getY());
        });

        screen.getDrawingCanvas().setOnMouseDragged(event -> {
            screen.getDrawingGraphics().lineTo(event.getX(), event.getY());
            screen.getDrawingGraphics().stroke();
        });

        screen.getDrawingCanvas().setOnMouseReleased(event -> {
            actionPerformed(screen);
        });
    }

    /**
     * Signals an event to corresponding event processors
     *
     * @param screen Canvas holder
     */
    public void actionPerformed(CanvasControl screen)
    {
        screen.onActionPerformed();
    }
}
