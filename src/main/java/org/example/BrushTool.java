package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker)
    {
        canvas.setOnMousePressed(event -> {
            graphics.beginPath();
            graphics.moveTo(event.getX(), event.getY());

            super.install(canvas, graphics, colorPicker);
        });

        canvas.setOnMouseDragged(event -> {
            graphics.lineTo(event.getX(), event.getY());
            graphics.stroke();
        });
    }
}
