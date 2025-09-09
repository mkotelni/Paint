package org.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PaintController {
    @FXML
    BorderPane borderPane;
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField brushSize;
    @FXML
    private TextField imageWidth;
    @FXML
    private TextField imageHeight;

    private Stage stage;

    private Screen screen;
    private FileMenu fileMenu;
    private AlertWindow alertWindow;

    /*----CONSTRUCTORS----*/
    public PaintController(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void initialize()
    {
        /*canvas.widthProperty().bind(borderPane.widthProperty());
        canvas.heightProperty().bind(borderPane.heightProperty());*/

        screen = new Screen(stage, canvas);
        fileMenu = new FileMenu(stage, canvas);
        alertWindow = new AlertWindow();

        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            alertWindow.handleExit(fileMenu);
        });
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

    //START OF HELPMENU BUTTONS
    public void onHelp()
    {
        alertWindow.showHelp();
    }

    public void onAbout()
    {
        alertWindow.showAbout();
    }
    //END OF HELPMENU BUTTONS

    //RESIZE BUTTON
    public void onResize()
    {
        double width = Double.parseDouble(imageWidth.getText());
        double height = Double.parseDouble(imageHeight.getText());

        screen.setImage(fileMenu.getImage());
        screen.drawImage(width, height);

        System.out.println(imageWidth.getText() + " " + imageHeight.getText());
    }

    /*TEXTFIELD ACTIONS*/
    public void onBrushSize()
    {
        screen.setBrushSize(Double.parseDouble(brushSize.getText()));
    }
}
