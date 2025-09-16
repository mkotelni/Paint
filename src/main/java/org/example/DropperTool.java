package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker)
    {
        canvas.setOnMouseClicked(event -> {
            Image snapshot = canvas.snapshot(null, null);
            PixelReader pixelReader = snapshot.getPixelReader();

            int x = (int) event.getX();
            int y = (int) event.getY();

            if (x < snapshot.getWidth() && y < snapshot.getHeight()) //mouse event is on canvas
            {
                Color color = pixelReader.getColor(x, y);
                colorPicker.setValue(color);
                graphics.setStroke(color);
            }

            //automatically revert to whatever tool was used before selecting dropper
            canvas.setOnMouseClicked(null);
            sizeableTool.install(canvas, graphics, colorPicker); //TODO: decide whether or not to implement a buffer to solve eraser fallback
        });
    }
}
