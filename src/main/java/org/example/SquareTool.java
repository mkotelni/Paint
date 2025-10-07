package org.example;

import javafx.geometry.Point2D;
import javafx.scene.control.ColorPicker;

public class SquareTool extends RectangleTool{

    public SquareTool()
    {
        super();
        getClickBox().makeSquare();
    }

    @Override
    public void install(Screen screen, ColorPicker colorPicker) {
        super.install(screen, colorPicker);
    }
}
