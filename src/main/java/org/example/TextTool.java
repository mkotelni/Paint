package org.example;

import javafx.scene.control.ColorPicker;
import javafx.scene.text.Font;

public class TextTool extends SizeableTool{
    private String text;

    public TextTool(String text) {this.text = text;}

    @Override
    public void install(Screen screen, ColorPicker colorPicker) {
        screen.getPreviewCanvas().setOnMousePressed(event -> {
            screen.getPreviewGraphics().setFill(colorPicker.getValue());
            screen.getPreviewGraphics().setFont(new Font(getSize()));

            screen.getDrawingGraphics().setFill(colorPicker.getValue());
            screen.getDrawingGraphics().setFont(new Font(getSize()));

            screen.getPreviewGraphics().fillText(text, event.getX(), event.getY());
        });

        screen.getPreviewCanvas().setOnMouseDragged(event -> {
            screen.clearCanvas(screen.getPreviewGraphics());
            screen.getPreviewGraphics().fillText(text, event.getX(), event.getY());
        });

        screen.getPreviewCanvas().setOnMouseReleased(event -> {
            screen.clearCanvas(screen.getPreviewGraphics());
            screen.getDrawingGraphics().fillText(text, event.getX(), event.getY());
        });
    }
}
