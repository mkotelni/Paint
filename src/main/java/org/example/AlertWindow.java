package org.example;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertWindow{

    public void showAbout()
    {
        Alert aboutWindow = new Alert(Alert.AlertType.INFORMATION);
        aboutWindow.setTitle("About");
        aboutWindow.setHeaderText("About this software:");
        aboutWindow.setContentText("Pain(t) v1.0.1\n\n" +
                                    "2025 Max Kotelnikov\n\n" +
                                    "Github: https://github.com/mkotelni/Paint.git");
        aboutWindow.showAndWait();
    }

    public void showHelp()
    {
        Alert helpWindow = new Alert(Alert.AlertType.INFORMATION);
        helpWindow.setTitle("Help");
        helpWindow.setHeaderText("How to use:");
        helpWindow.setContentText("File menu:\n" +
                                    "Load and save images through the file menu\n\n" +
                                    "Drawing:\n" +
                                    "To draw, click and drag the mouse\n" +
                                    "To change brush size, input desired size in the Brush Size text field and press enter\n" +
                                    "To change color, click the color dropdown and choose a color\n" +
                                    "To resize an image, input width and height to their respective text fields and click the resize button");
        helpWindow.showAndWait();
    }

    /**
     *
     * @param fileMenu A FileMenu object
     */
    public void handleExit(FileMenu fileMenu)
    {
        Alert exitHandler = new Alert(Alert.AlertType.ERROR);
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
}
