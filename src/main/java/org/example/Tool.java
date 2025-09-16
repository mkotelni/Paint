package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;


public interface Tool {
    //will set whatever action will be made on whatever mouse event
    void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker);
}
