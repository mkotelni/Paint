package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class Screen {
    private Canvas canvas;
    private Image image;
    private Stage stage;
    private GraphicsContext graphics;

    private double brushSize;

    /*----CONSTRUCTORS----*/
    public Screen() {}
    public Screen(Stage stage) {this.stage = stage;}

    public Screen(Stage stage, Canvas canvas)
    {
        this.stage  = stage;
        this.canvas = canvas;
        graphics = this.canvas.getGraphicsContext2D();

        brushSize = 1;
        graphics.setLineCap(StrokeLineCap.ROUND);
        //setMouseEvents(); DEPRECATED
    }

    /*----GETTERS----*/
    public Image getImage() {return image;}
    public Canvas getCanvas() {return canvas;}
    public Stage getStage() {return stage;}
    public GraphicsContext getGraphics() {return graphics;}
    public double getBrushSize() {return brushSize;}

    /*----SETTERS----*/
    public void setImage(Image image) {this.image = image;}
    public void setCanvas(Canvas canvas) {this.canvas = canvas;}
    public void setStage(Stage stage) {this.stage = stage;}
    public void setGraphics(GraphicsContext graphics) {this.graphics = graphics;}
    public void setBrushSize(double size) {this.brushSize = size; graphics.setLineWidth(brushSize);}

    /*----HELPER FUNCTIONS----*/

    /**
     * Draws an image file to the canvas
     */
    //draws an image to the canvas
    public void drawImage()
    {
            graphics.clearRect(0,0, canvas.getWidth(), canvas.getHeight()); //clear canvas
            graphics.drawImage(image, 0, 0);
    }

    /**
     * Draws an image file scaled to a given width and height
     *
     * @param width Resized image width
     * @param height Resized image height
     */
    //draws an image scaled to user specified width/height
    public void drawImage(double width, double height)
    {
        graphics.clearRect(0,0, canvas.getWidth(), canvas.getHeight()); //clear canvas
        graphics.drawImage(image, 0, 0, width, height);
    }
}
