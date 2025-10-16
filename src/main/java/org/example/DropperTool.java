package org.example;

import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class DropperTool implements Tool{
    private SizeableTool sizeableTool;

    /**
     * Constructor for DropperTool that keeps track of the tool used before it
     *
     * @param sizeableTool The tool used before selecting the dropper
     */
    public DropperTool(SizeableTool sizeableTool) {this.sizeableTool = sizeableTool;}

    public void install(CanvasControl screen, ColorPicker colorPicker)
    {
        screen.getDrawingCanvas().setOnMouseClicked(event -> {
            Image snapshot = screen.getDrawingCanvas().snapshot(null, null);
            PixelReader pixelReader = snapshot.getPixelReader();

            int x = (int) event.getX();
            int y = (int) event.getY();

            if (x < snapshot.getWidth() && y < snapshot.getHeight()) //mouse event is on canvas
            {
                Color color = pixelReader.getColor(x, y);
                colorPicker.setValue(color);
                screen.getDrawingGraphics().setStroke(color);
            }

            //automatically revert to whatever tool was used before selecting dropper
            screen.getDrawingCanvas().setOnMouseClicked(null);
            sizeableTool.install(screen, colorPicker); //TODO: decide whether or not to implement a buffer to solve eraser fallback
        });
    }
}
