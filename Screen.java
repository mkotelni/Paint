package org.example;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class Screen {
    private Canvas canvas;
    private Image image;
    private Stage stage;
    private GraphicsContext graphics;

    private double brushSize;

    /*----CONSTRUCTORS----*/
    public Screen() {}
    public Screen(Stage stage) {
        this.stage = stage;
    }

    public Screen(Stage stage, Canvas canvas)
    {
        this.stage  = stage;
        this.canvas = canvas;
        graphics = this.canvas.getGraphicsContext2D();

        brushSize = 1;
        graphics.setLineCap(StrokeLineCap.ROUND);
        setMouseEvents();
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
    //draws an image to the canvas
    public void drawImage()
    {
            canvas.setWidth(image.getWidth());
            canvas.setHeight(image.getHeight());

            graphics.clearRect(0,0, canvas.getWidth(), canvas.getHeight()); //clear canvas
            graphics.drawImage(image, 0, 0);
    }

    //draws an image scaled to user specified width/height
    public void drawImage(double width, double height)
    {
        canvas.setWidth(width);
        canvas.setHeight(height);

        graphics.clearRect(0,0, canvas.getWidth(), canvas.getHeight()); //clear canvas
        graphics.drawImage(image, 0, 0, width, height);
    }

    private void setMouseEvents()
    {
        final double[] lastX = new double[1];
        final double[] lastY = new double[1];

        /*----MOUSE EVENTS----*/
        //start location before dragging
        canvas.setOnMousePressed(event -> {
            lastX[0] = event.getX();
            lastY[0] = event.getY();
        });

        //draws a smooth continuous line
        canvas.setOnMouseDragged(event -> {
            double x = event.getX();
            double y = event.getY();

            graphics.strokeLine(lastX[0], lastY[0], x, y);

            lastX[0] = x;
            lastY[0] = y;
        });
    }
}
