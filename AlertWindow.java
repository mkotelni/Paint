package org.example;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertWindow{

    public void showAbout()
    {
        Alert aboutWindow = new Alert(Alert.AlertType.INFORMATION);
        aboutWindow.setTitle("About");
        aboutWindow.setHeaderText("");
        aboutWindow.setContentText("");
        aboutWindow.showAndWait();
    }

    public void showHelp()
    {
        Alert helpWindow = new Alert(Alert.AlertType.INFORMATION);
        helpWindow.setTitle("How to use");
        helpWindow.setHeaderText("");
        helpWindow.setContentText("");
        helpWindow.showAndWait();
    }

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
            Platform.exit();
        });
    }
}
