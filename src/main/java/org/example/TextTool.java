package org.example;

import javafx.scene.control.ColorPicker;
import javafx.scene.text.Font;

//TODO: make text a sizeable tool and make font scale with size
public class TextTool implements Tool{
    private String text;

    public TextTool(String text) {this.text = text;}

    @Override
    public void install(Screen screen, ColorPicker colorPicker) {
        screen.getDrawingCanvas().setOnMouseClicked(event -> {
            screen.getDrawingGraphics().setFill(colorPicker.getValue());
            screen.getDrawingGraphics().setFont(new Font(20));
            screen.getDrawingGraphics().fillText(text, event.getX(), event.getY());

        });
    }
}
