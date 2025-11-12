package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyEvent;

/**
 * The TriangleTool class is a sizeable tool that is used to draw equilateral, isosceles, and right triangles
 */
public class TriangleTool extends ShapeTool{
    private final int X_POINTS = 0;
    private final int Y_POINTS = 1;
    private final int NUMPOINTS = 3;

    double[][] points = new double[2][3];

    private boolean isCtrlDown = false;
    private boolean isShiftDown = false;

    /**
     * Creates a triangle tool
     *
     * @param screen Canvas holder
     * @param colorPicker ColorPicker object
     */
    @Override
    public void install(CanvasControl screen, ColorPicker colorPicker) {
        super.install(screen, colorPicker);

        screen.getPreviewCanvas().getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown())
                isCtrlDown = true;
            if (event.isShiftDown())
                isShiftDown = true;

            screen.clearCanvas(screen.getPreviewGraphics());
            drawShape(screen.getPreviewGraphics());
        });

        screen.getPreviewCanvas().getScene().addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (!event.isControlDown())
                isCtrlDown = false;
            if (!event.isShiftDown())
                isShiftDown = false;

            screen.clearCanvas(screen.getPreviewGraphics());
            drawShape(screen.getPreviewGraphics());
        });
    }

    /**
     * Draws a triangle
     *
     * @param graphics the GraphicsContext associated with the layer to be drawn to
     */
    @Override
    public void drawShape(GraphicsContext graphics) {
        if (!isDrawing())
            return;

        points = calculatePolygonPoints(NUMPOINTS);
        graphics.strokePolygon(points[X_POINTS], points[Y_POINTS], NUMPOINTS);
    }

    //TODO: make it so that a right triangle is drawn correctly in all 4 quadrants
    /**
     * Calculates the point positions of the triangle
     *
     * @param numPoints the number of points of the shape
     * @return a 2D array with the first row consisting of the calculated x-coordinates, and the second row with the calculated y-coordinates
     */
    @Override
    public double[][] calculatePolygonPoints(int numPoints) {
        Point2D origin = getClickBox().getOrigin();

        if (isCtrlDown)
            getClickBox().makeSquare(); //TODO: "equilateral" triangle is technically isosceles, either top y-point needs to be calculated differently or the bottom 2, or just call default polygon
        else
            getClickBox().makeRectangular();

        double[] xPoints = {origin.getX(), origin.getX() + getClickBox().getWidth() / 2, origin.getX() + getClickBox().getWidth()};
        double[] yPoints = {origin.getY() + getClickBox().getHeight(), origin.getY(), origin.getY() + getClickBox().getHeight()};

        if (isShiftDown)
        {
            xPoints = new double[] {origin.getX(), origin.getX() + getClickBox().getWidth(), origin.getX()};
            yPoints = new double[] {origin.getY(), origin.getY(), origin.getY() + getClickBox().getHeight()};
        }

        return new double[][] {xPoints, yPoints};
    }
}
