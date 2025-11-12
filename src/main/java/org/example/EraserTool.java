package org.example;

import javafx.scene.control.ColorPicker;

/**
 * The EraserTool class is a sizeable tool used to erase drawings made on the drawing layer
 */
public class EraserTool extends SizeableTool{
    double lastX, lastY;

    /**
     * Creates an eraser tool with a default width of 1 pixel
     */
    public EraserTool() {setSize(1);}

    /**
     * Creates an eraser tool with a specific width
     *
     * @param size Eraser size
     */
    public EraserTool(double size) {setSize(size);}

    /**
     * Configures the eraser tool
     *
     * @param screen Canvas holder
     * @param colorPicker ColorPicker object
     */
    public void install(CanvasControl screen, ColorPicker colorPicker)
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
            //END OF GPT CODE

            lastX = x;
            lastY = y;
        });

        screen.getDrawingCanvas().setOnMouseReleased(event -> {
            actionPerformed(screen);
        });
    }

    /**
     * Signals an event to corresponding event processors
     *
     * @param screen Canvas holder
     */
    public void actionPerformed(CanvasControl screen)
    {
        screen.onActionPerformed();
    }
}
