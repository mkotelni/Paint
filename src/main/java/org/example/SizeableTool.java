package org.example;

import javafx.scene.control.ColorPicker;

/**
 * The SizeableTool class is a generic tool class that contains basic information for all tools with a size
 */
public class SizeableTool implements Tool{
    private double size;

    /**
     * Configures a tool's width and color
     *
     * @param screen Canvas holder
     * @param colorPicker ColorPicker object
     */
    public void install(CanvasControl screen, ColorPicker colorPicker)
    {
        //set up each editable canvas
        screen.getDrawingGraphics().setStroke(colorPicker.getValue());
        screen.getDrawingGraphics().setLineWidth(getSize());

        screen.getPreviewGraphics().setStroke(colorPicker.getValue());
        screen.getPreviewGraphics().setLineWidth(getSize());

        //actionPerformed(screen); //TODO: maybe make 2 installs to account for mouseMoved installations
    }

    /**
     * Signals an event to corresponding event processors
     *
     * @param screen Canvas holder
     */
    public void actionPerformed(CanvasControl screen){
        screen.onActionPerformed();
    }

    //getters/setters
    /**
     * Returns the size of the tool
     *
     * @return the size
     */
    public double getSize() {return size;}

    /**
     * Sets the size of a tool
     *
     * @param size the size
     */
    public void setSize(double size) {this.size = size;}

}
