package org.example;

import javafx.scene.control.ColorPicker;

/**
 * The Tool interface defines key methods for every tool
 */
public interface Tool {
    /**
     * Configures the tool's attributes and actions depending on what event is performed by the user
     *
     * @param screen Canvas holder
     * @param colorPicker ColorPicker object
     */
    void install(CanvasControl screen, ColorPicker colorPicker);

    /**
     * Signals an event to corresponding event processors
     *
     * @param screen Canvas holder
     */
    void actionPerformed(CanvasControl screen);
}
