package org.example;

import javafx.geometry.Point2D;
import javafx.scene.control.ColorPicker;

public class RectangleTool extends SizeableTool{
    private Point2D firstPoint;
    private ClickBox clickBox;

    public RectangleTool() {clickBox = new ClickBox();}

    public ClickBox getClickBox() {
        return clickBox;
    }

    @Override
    public void install(Screen screen, ColorPicker colorPicker)
    {

        screen.getPreviewCanvas().setOnMousePressed(event -> {
            super.install(screen, colorPicker);
            if (firstPoint == null)
                firstPoint = new Point2D(event.getX(), event.getY());
        });

        screen.getPreviewCanvas().setOnMouseDragged(event -> {
            screen.clearCanvas(screen.getPreviewGraphics());
            clickBox.update(firstPoint, new Point2D(event.getX(), event.getY()));
            screen.getPreviewGraphics().strokeRect(clickBox.getOrigin().getX(), clickBox.getOrigin().getY(), clickBox.getWidth(), clickBox.getHeight());
        });

        screen.getPreviewCanvas().setOnMouseReleased(event -> {
            screen.clearCanvas(screen.getPreviewGraphics());
            clickBox.update(firstPoint, new Point2D(event.getX(), event.getY()));
            screen.getDrawingGraphics().strokeRect(clickBox.getOrigin().getX(), clickBox.getOrigin().getY(), clickBox.getWidth(), clickBox.getHeight());

            firstPoint = null;
        });
    }
}
