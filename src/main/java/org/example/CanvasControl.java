package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/**
 * The CanvasControl class holds all of the canvas layers and controls access to them and their associated GraphicsContexts.
 * The class also controls any previous/succeeding states, and the state of what's shown onscreen through image drawing and clearing.
 * Future references of this class are usually referred to as "screen" because this class represents what is shown on screen.
 */
public class CanvasControl {
    private StackPane canvasStack;
    private Canvas imageCanvas;
    private Canvas drawingCanvas;
    private Canvas previewCanvas;

    private GraphicsContext imageGraphics;
    private GraphicsContext drawingGraphics;
    private GraphicsContext previewGraphics;

    private Stage stage;

    private CanvasStateManager canvasStateManager;

    /*----CONSTRUCTORS----*/
    /**
     * Creates a CanvasController
     *
     * @param stage MainFX window
     * @param canvasStack Canvas stackpane
     * @param imageCanvas Image layer
     * @param drawingCanvas Drawing layer
     * @param previewCanvas Live-preview layer
     */
    public CanvasControl(Stage stage, StackPane canvasStack, Canvas imageCanvas, Canvas drawingCanvas, Canvas previewCanvas)
    {
        this.stage = stage;
        this.canvasStack = canvasStack;
        canvasStateManager = new CanvasStateManager(this);

        this.imageCanvas = imageCanvas;
        imageGraphics = this.imageCanvas.getGraphicsContext2D();

        //make the image layer white to make the drawable surface more obvious
        imageGraphics.setFill(Color.WHITE);
        imageGraphics.fillRect(0, 0, imageCanvas.getWidth(), imageCanvas.getHeight());
        imageGraphics.setFill(null);

        this.drawingCanvas = drawingCanvas;
        drawingGraphics = this.drawingCanvas.getGraphicsContext2D();

        this.previewCanvas = previewCanvas;
        previewGraphics = this.previewCanvas.getGraphicsContext2D();

        //make paint strokes round not square, looks better
        drawingGraphics.setLineCap(StrokeLineCap.ROUND);
        previewGraphics.setLineCap(StrokeLineCap.ROUND);
    }

    /*----GETTERS----*/

    /**
     * Returnes a snapshot of the entire stackpane
     *
     * @return the snapshot of the entire stack pane
     */
    public Image getSnapshot() {return canvasStack.snapshot(null, null);}

    /**
     * Returns the image layer
     *
     * @return the image layer
     */
    public Canvas getImageCanvas() {return imageCanvas;}

    /**
     * Returns the drawing layer
     *
     * @return the drawing layer
     */
    public Canvas getDrawingCanvas() {return drawingCanvas;}

    /**
     * Returns the live-preview layer
     *
     * @return the live-preview layer
     */
    public Canvas getPreviewCanvas() {return previewCanvas;}

    /**
     * Returns the GraphicsContext related to the drawing layer
     *
     * @return the GraphicsContext related to the drawing layer
     */
    public GraphicsContext getDrawingGraphics() {return drawingGraphics;}

    /**
     * Returns the GraphicsContext related to the live-preview layer
     *
     * @return the GraphicsContext related to the live-preview layer
     */
    public GraphicsContext getPreviewGraphics() {return previewGraphics;}

    /**
     * Returns the object that manages undo/redos
     *
     * @return the undo/redo manager
     */
    public CanvasStateManager getCanvasStateManager(){return canvasStateManager;}

    /**
     * Returns the main window
     *
     * @return the main window
     */
    public Stage getStage() {return stage;}

    /*----SETTERS----*/
    public void setDrawingCanvas(Canvas drawingCanvas) {this.drawingCanvas = drawingCanvas;}
    public void setStage(Stage stage) {this.stage = stage;}
    public void setDrawingGraphics(GraphicsContext drawingGraphics) {this.drawingGraphics = drawingGraphics;}

    /*----HELPER FUNCTIONS----*/
    /**
     * Draws an image file to the drawingCanvas
     *
     * @param image the image to be drawn
     */
    //TODO: for all overloaded functions, fix bug where the image is drawn at the borderpane origin not the canvas origin
    public void drawImage(Image image)
    {
            imageGraphics.clearRect(0,0, imageCanvas.getWidth(), imageCanvas.getHeight()); //clear imageCanvas
            imageGraphics.drawImage(image, 0, 0);
    }

    /**
     * Draws an image scaled to a given width and height
     *
     * @param width Resized image width
     * @param height Resized image height
     */
    public void drawImage(Image image, double width, double height)
    {
        imageGraphics.clearRect(0,0, imageCanvas.getWidth(), imageCanvas.getHeight()); //clear imageCanvas

        drawingCanvas.setWidth(width);
        drawingCanvas.setHeight(height);

        imageGraphics.drawImage(image, 0, 0, width, height);
    }

    /**
     * Clears a specific canvas layer
     *
     * @param graphics the GraphicsContext associated with a canvas layer
     */
    //TODO: clear filemenu's image after clear canvas
    public void clearCanvas(GraphicsContext graphics)
    {
        graphics.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight()); //all canvas dimensions are bound to drawingCanvas
    }

    /**
     * Clears all canvas layers
     */
    public void clearAll()
    {
        //all canvas dimensions are bound to drawingCanvas
        imageGraphics.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
        drawingGraphics.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
        previewGraphics.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());

        //make editable area obvious
        imageGraphics.setFill(Color.WHITE);
        imageGraphics.fillRect(0, 0, imageCanvas.getWidth(), imageCanvas.getHeight());
        imageGraphics.setFill(null);
    }

    /**
     * Handles actions performed by tools to be incorporated into the undo stack
     */
    public void onActionPerformed()
    {
        canvasStateManager.addState();
    }
}
