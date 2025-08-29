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
        Scene scene = new Scene(loader.load());

        PaintController controller = loader.getController();

        controller.setStage(stage);

        //stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("paint.fxml")))); //redundant???
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
