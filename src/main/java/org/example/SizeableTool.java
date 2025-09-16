package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class SizeableTool implements Tool{
    private double size;

    /**
     * Sets a tool's color and width
     *
     * @param canvas Current canvas
     * @param graphics Graphics used for drawing
     * @param colorPicker ColorPicker object
     */
    //install method just sets line width and color
    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker)
    {
        graphics.setStroke(colorPicker.getValue());
        graphics.setLineWidth(getSize());
    }

    //getters/setters
    public double getSize() {return size;}
    public void setSize(double size) {this.size = size;}

}
