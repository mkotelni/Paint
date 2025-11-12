package org.example;

import javafx.scene.canvas.GraphicsContext;

/**
 * The RectangleTool class is a sizeable tool used to draw rectangles
 */
public class RectangleTool extends ShapeTool{

    /**
     * Draws a rectangle
     *
     * @param graphics the GraphicsContext associated with the layer to be drawn to
     */
    @Override
    public void drawShape(GraphicsContext graphics) {
        graphics.strokeRect(getClickBox().getOrigin().getX(), getClickBox().getOrigin().getY(), getClickBox().getWidth(), getClickBox().getHeight());
    }
}
