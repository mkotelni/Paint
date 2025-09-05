package org.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class PaintController {
    @FXML
    private Canvas canvas;

    private FileMenu fileMenu;

    /*----CONSTRUCTORS----*/
    public PaintController(Stage stage)
    {
        fileMenu = new FileMenu(stage);
    }

    @FXML
    private void initialize()
    {
        fileMenu.setCanvas(canvas);
    }

    /*----BUTTON ACTIONS----*/
    //BEGINNING OF FILEMENU BUTTONS
    public void onSave()
    {
        fileMenu.save();
    }

    public void onSaveAs()
    {
        fileMenu.saveAs();
    }

    public void onExit()
    {
        Platform.exit();
    }

    public void onLoadImage()
    {
        fileMenu.loadImage();
    }
    //END OF FILEMENU BUTTONS
}
