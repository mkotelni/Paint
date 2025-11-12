package org.example;

import javafx.scene.canvas.GraphicsContext;

/**
 * The EllipseTool class is a sizeable tool used to draw ellipses
 */
public class EllipseTool extends ShapeTool{

    @Override
    /**
     * Draws an ellipse
     *
     * @param graphics the GraphicsContext associated with the layer to be drawn to
     */
    public void drawShape(GraphicsContext graphics) {
        graphics.strokeOval(getClickBox().getOrigin().getX(), getClickBox().getOrigin().getY(), getClickBox().getWidth(), getClickBox().getHeight());
    }
}
