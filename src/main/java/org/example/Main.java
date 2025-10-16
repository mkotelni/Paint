package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("paint.fxml"));
        loader.setControllerFactory(param -> new PaintController(stage));

        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.show();
    }

    //Why did the Java programmer quit his job?
    //Because he did not get arrays (Ha, Ha)
    public static void main(String[] args)
    {
        launch(args);
    }
}
