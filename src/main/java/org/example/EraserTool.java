package org.example;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class EraserTool extends SizeableTool{
    double lastX, lastY;

    /**
     * Constructor for EraserTool with a default width of 1 pixel
     */
    public EraserTool() {setSize(1);}

    /**
     * Constructor for EraserTool specifying eraser width
     *
     * @param size Eraser size
     */
    public EraserTool(double size) {setSize(size);}

    public void install(Screen screen, ColorPicker colorPicker)
    {
        screen.getDrawingCanvas().toFront(); //eraser has no live preview mode

        screen.getDrawingCanvas().setOnMousePressed(event -> {
            lastX = event.getX();
            lastY = event.getY();

            screen.getDrawingGraphics().setLineWidth(getSize()); //needs to only update width, has no color
        });

        screen.getDrawingCanvas().setOnMouseDragged(event -> {
            double x = event.getX();
            double y = event.getY();

            // FOLLOWING CODE WAS WRITTEN BY CHATGPT (thank you!!!) - Clear along a line between last point and current
            double dx = x - lastX;
            double dy = y - lastY;
            double distance = Math.hypot(dx, dy);

            //iterate along each pixel between current point and last point
            double steps = distance / (getSize() / 2.0);
            for (int i = 0; i <= steps; i++) {
                double px = lastX + dx * (i / steps);
                double py = lastY + dy * (i / steps);

                screen.getDrawingGraphics().clearRect( //erase as a square centered on the cursor
                        px - getSize() / 2,
                        py - getSize() / 2,
                        getSize(),
                        getSize()
                );
            }

            lastX = x;
            lastY = y;
        });
    }
}
