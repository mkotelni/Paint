package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class SizeableTool implements Tool{
    private double size;

    /**
     * Sets a tool's color and width
     *
     * @param screen Canvas holder
     * @param colorPicker ColorPicker object
     */
    //install method just sets line width and color
    public void install(Screen screen, ColorPicker colorPicker)
    {
        //set up each editable canvas
        screen.getDrawingGraphics().setStroke(colorPicker.getValue());
        screen.getDrawingGraphics().setLineWidth(getSize());

        screen.getPreviewGraphics().setStroke(colorPicker.getValue());
        screen.getPreviewGraphics().setLineWidth(getSize());
    }

    //getters/setters
    public double getSize() {return size;}
    public void setSize(double size) {this.size = size;}

}
