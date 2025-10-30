package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class CanvasControl {
    private StackPane canvasStack;
    private Canvas imageCanvas;
    private Canvas drawingCanvas;
    private Canvas previewCanvas;

    private GraphicsContext imageGraphics;
    private GraphicsContext drawingGraphics;
    private GraphicsContext previewGraphics;

    private Image image; //REDUNDANT?
    private Stage stage;

    private CanvasStateManager canvasStateManager;

    /*----CONSTRUCTORS----*/
    public CanvasControl() {}
    public CanvasControl(Stage stage) {this.stage = stage;}

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
    public Image getImage() {return image;}
    public Image getSnapshot() {return canvasStack.snapshot(null, null);}
    public Canvas getDrawingCanvas() {return drawingCanvas;}
    public Canvas getPreviewCanvas() {return previewCanvas;}
    public GraphicsContext getDrawingGraphics() {return drawingGraphics;}
    public GraphicsContext getPreviewGraphics() {return previewGraphics;}
    public CanvasStateManager getCanvasStateManager(){return canvasStateManager;}
    public Stage getStage() {return stage;}

    /*----SETTERS----*/
    public void setImage(Image image) {this.image = image;}
    public void setDrawingCanvas(Canvas drawingCanvas) {this.drawingCanvas = drawingCanvas;}
    public void setStage(Stage stage) {this.stage = stage;}
    public void setDrawingGraphics(GraphicsContext drawingGraphics) {this.drawingGraphics = drawingGraphics;}

    /*----HELPER FUNCTIONS----*/
    /**
     * Draws an image file to the drawingCanvas
     */
    //draws an image file to the drawingCanvas
    public void drawImage(Image image)
    {
            imageGraphics.clearRect(0,0, imageCanvas.getWidth(), imageCanvas.getHeight()); //clear imageCanvas
            imageGraphics.drawImage(image, 0, 0);
    }

    /**
     * Draws an image file scaled to a given width and height
     *
     * @param width Resized image width
     * @param height Resized image height
     */
    //draws an image  scaled to user specified width/height
    public void drawImage(Image image, double width, double height)
    {
        imageGraphics.clearRect(0,0, imageCanvas.getWidth(), imageCanvas.getHeight()); //clear imageCanvas

        drawingCanvas.setWidth(width);
        drawingCanvas.setHeight(height);

        imageGraphics.drawImage(image, 0, 0, width, height);
    }

    //clears a canvas using the gc that is attached to it
    public void clearCanvas(GraphicsContext graphics)
    {
        graphics.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight()); //all canvas dimensions are bound to drawingCanvas
    }

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

    public void onActionPerformed()
    {
        canvasStateManager.addState();
    }
}
