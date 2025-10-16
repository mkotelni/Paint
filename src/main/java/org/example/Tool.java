package org.example;

import javafx.scene.control.ColorPicker;


public interface Tool {
    //will set whatever action will be made on whatever mouse event
    void install(CanvasControl screen, ColorPicker colorPicker);
}
