package org.example;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Screen {
    private Canvas canvas;
    private Image image;
    private Stage stage;
    private GraphicsContext gc; //needed?

    /*----CONSTRUCTORS----*/
    public Screen() {}
    public Screen(Stage stage) {this.stage = stage;}

    /*----GETTERS----*/
    public Image getImage() {return image;}
    public Canvas getCanvas() {return canvas;}
    public Stage getStage() {return stage;}

    /*----SETTERS----*/
    public void setImage(Image image) {this.image = image;}
    public void setCanvas(Canvas canvas) {this.canvas = canvas;}
    public void setStage(Stage stage) {this.stage = stage;}

    /*----HELPER FUNCTIONS----*/
    //draws an image to the canvas
    public void drawImage()
    {
            canvas.setWidth(image.getWidth());
            canvas.setHeight(image.getHeight());

            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight()); //clear canvas
            gc.drawImage(image, 0, 0);
    }
}
