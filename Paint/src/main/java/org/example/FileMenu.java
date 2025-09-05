package org.example;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

public class FileMenu extends Screen{
    private FileChooser fileChooser;
    private File file;

    /*----CONSTRUCTORS----*/
    public FileMenu(Stage stage)
    {
        super(stage);
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", ".bmp"));
    }

    /*----GETTERS----*/
    public File getFile() {return file;}
    public FileChooser getFileChooser() {return fileChooser;}

    /*----SETTERS----*/
    public void setFile(File file) {this.file = file;}
    public void setFileChooser(FileChooser fileChooser) {this.fileChooser = fileChooser;}


    /*----HELPER FUNCTIONS----*/

    //writes whatever's on screen to a given file path
    private void writeImage(File file) //precondition: file != null
    {
        try
        {
            Image snapshot = getCanvas().snapshot(null, null);

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        }
        catch (Exception e)
        {
            System.out.println("Failed to save image: " + e);
        }
    }

    //save whatever's on screen to current file path
    public void save() {
        if (file != null)
            writeImage(file);
        else
            System.out.println("File was not chosen (file is null)");
    }

    //save whatever's on screen as a new file
    public void saveAs() {
        fileChooser.setTitle("Save as");
        file = fileChooser.showSaveDialog(getStage());

        if (file != null)
            writeImage(file);
    }

    //select and display an image file
    public void loadImage()
    {
        fileChooser.setTitle("Select Image File");
        file = fileChooser.showOpenDialog(getStage());

        if (file != null)
        {
            setImage(new Image(file.toURI().toString()));
            drawImage();
        }
    }
}
