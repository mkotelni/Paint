package org.example;

import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

/**
 * The DropperTool class is a tool used to select the color of a pixel on screen
 */
public class DropperTool implements Tool{
    private SizeableTool sizeableTool;

    /**
     * Creates a dropper tool
     *
     * @param sizeableTool The tool used before selecting the dropper
     */
    //TODO: previous tool should probably be a generic tool, not sizeable
    public DropperTool(SizeableTool sizeableTool) {this.sizeableTool = sizeableTool;}

    /**
     * Configures the dropper tool
     *
     * @param screen Canvas holder
     * @param colorPicker ColorPicker object
     */
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

            actionPerformed(screen);
        });
    }

    public void actionPerformed(CanvasControl screen)
    {
        //TBD
    }
}
