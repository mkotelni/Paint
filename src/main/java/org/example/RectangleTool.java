package org.example;

import javafx.scene.canvas.GraphicsContext;

public class RectangleTool extends ShapeTool{

    @Override
    public void drawShape(GraphicsContext graphics) {
        graphics.strokeRect(getClickBox().getOrigin().getX(), getClickBox().getOrigin().getY(), getClickBox().getWidth(), getClickBox().getHeight());
    }
}
