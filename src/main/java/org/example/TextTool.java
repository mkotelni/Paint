package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.text.Font;

public class TextTool implements Tool{
    private String text;

    public TextTool(String text) {this.text = text;}

    @Override
    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker) {
        canvas.setOnMouseClicked(event -> {
            graphics.setFill(colorPicker.getValue());
            graphics.setFont(new Font(20));
            graphics.fillText(text, event.getX(), event.getY());

        });
    }
}
