package org.example;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

//TODO: think about making all the functions static, since there's no need for a constructor

/**
 * The AlertWindow class manages various popups to provide the user with information and ask for confirmation for various actions
 */
public class AlertWindow{

    /**
     * Display popup with information about the software
     */
    public void showAbout()
    {
        Alert aboutWindow = new Alert(Alert.AlertType.INFORMATION);
        aboutWindow.setTitle("About");
        aboutWindow.setHeaderText("About this software:");
        aboutWindow.setContentText("""
                Pain(t) v1.2.7
                
                2025 Max Kotelnikov
                
                Github: https://github.com/mkotelni/Paint.git""");
        aboutWindow.showAndWait();
    }

    /**
     * Display popup with information on how to use the software
     */
    public void showHelp()
    {
        Alert helpWindow = new Alert(Alert.AlertType.INFORMATION);
        helpWindow.setTitle("Help");
        helpWindow.setHeaderText("How to use:");
        helpWindow.setContentText("""
                File menu:
                Load and save images through the file menu
                
                Drawing:
                To draw, click and drag the mouse
                To change brush size, input desired size in the Brush Size text field and press enter
                To change color, click the color dropdown and choose a color
                To resize an image, input width and height to their respective text fields and click the resize button""");
        helpWindow.showAndWait();
    }

    /**
     * Smart-save popup that asks for confirmation to exit without saving
     *
     * @param fileMenu A FileMenu object
     */
    public void handleExit(FileMenu fileMenu)
    {
        Alert exitHandler = new Alert(Alert.AlertType.WARNING);
        exitHandler.setTitle("Unsaved changes");
        exitHandler.setHeaderText("Are you sure you want to exit without saving?");
        exitHandler.setContentText("Unsaved changes will be lost");

        ButtonType save = new ButtonType("Save");
        ButtonType noSave = new ButtonType("Exit without saving");
        ButtonType cancel = new ButtonType("Cancel");

        exitHandler.getButtonTypes().setAll(save, noSave, cancel);

        exitHandler.showAndWait().ifPresent(response -> {
            if (response == save)
                fileMenu.saveAs();
            else if (response == noSave)
                Platform.exit();
        });
    }

    /**
     * Warning popup asking for confirmation to perform a potentially lossy conversion when saving
     *
     * @return boolean representing user confirmation
     */
    public boolean handleConversionWarning()
    {
        boolean[] proceed = {false};

        Alert conversionHandler = new Alert(Alert.AlertType.WARNING);
        conversionHandler.setTitle("Lossy image conversion");
        conversionHandler.setHeaderText("Are you sure you want to convert?");
        conversionHandler.setContentText("Saving to this format may result in data loss");

        ButtonType confirm = new ButtonType("Confirm");
        ButtonType cancel = new ButtonType("Cancel");

        conversionHandler.getButtonTypes().setAll(confirm, cancel);

        conversionHandler.showAndWait().ifPresent(response -> {
            if (response == confirm)
                proceed[0] = true;
        });

        return proceed[0];
    }

    /**
     * Warning popup asking for confirmation to create a blank canvas
     *
     * @return boolean representing user confirmation
     */
    public boolean handleNewCanvasWarning()
    {
        boolean[] proceed = {false};

        Alert newCanvasHandler = new Alert(Alert.AlertType.WARNING);
        newCanvasHandler.setTitle("New Canvas");
        newCanvasHandler.setHeaderText("Are you sure you want to make a new canvas?");
        newCanvasHandler.setContentText("Everything on-screen will be erased");

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");

        newCanvasHandler.getButtonTypes().setAll(yes, no);

        newCanvasHandler.showAndWait().ifPresent(response -> {
            if (response == yes)
                proceed[0] = true;
        });

        return proceed[0];
    }
}
