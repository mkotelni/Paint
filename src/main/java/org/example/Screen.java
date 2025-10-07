package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

//TODO: change screen to CanvasControl
public class Screen {
    private Canvas imageCanvas;
    private Canvas drawingCanvas;
    private Canvas previewCanvas;

    private GraphicsContext imageGraphics;
    private GraphicsContext drawingGraphics;
    private GraphicsContext previewGraphics;

    private Image image;
    private Stage stage;

    private double brushSize;

    /*----CONSTRUCTORS----*/
    public Screen() {}
    public Screen(Stage stage) {this.stage = stage;}

    public Screen(Stage stage, Canvas imageCanvas, Canvas drawingCanvas, Canvas previewCanvas)
    {
        this.stage = stage;

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

        //brushSize = 1; //brushSize REDUNDANT?

        //make paint strokes round not square, looks better
        drawingGraphics.setLineCap(StrokeLineCap.ROUND);
        previewGraphics.setLineCap(StrokeLineCap.ROUND);
    }

    /*----GETTERS----*/
    public Image getImage() {return image;}
    public Canvas getDrawingCanvas() {return drawingCanvas;}
    public Canvas getPreviewCanvas() {return previewCanvas;}
    public Stage getStage() {return stage;}
    public GraphicsContext getDrawingGraphics() {return drawingGraphics;}
    public GraphicsContext getPreviewGraphics() {return previewGraphics;}
    public double getBrushSize() {return brushSize;}

    /*----SETTERS----*/
    public void setImage(Image image) {this.image = image;}
    public void setDrawingCanvas(Canvas drawingCanvas) {this.drawingCanvas = drawingCanvas;}
    public void setStage(Stage stage) {this.stage = stage;}
    public void setDrawingGraphics(GraphicsContext drawingGraphics) {this.drawingGraphics = drawingGraphics;}
    public void setBrushSize(double size) {this.brushSize = size; drawingGraphics.setLineWidth(brushSize);}

    /*----HELPER FUNCTIONS----*/

    /**
     * Draws an image file to the drawingCanvas
     */
    //draws an image file to the drawingCanvas
    public void drawImage()
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
    public void drawImage(double width, double height)
    {
        imageGraphics.clearRect(0,0, imageCanvas.getWidth(), imageCanvas.getHeight()); //clear imageCanvas
        imageGraphics.drawImage(image, 0, 0, width, height);
    }

    //clears a canvas using the gc that is attached to it
    public void clearCanvas(GraphicsContext graphics)
    {
        graphics.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
    }
}
