package org.example;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

public class FileMenu extends Screen{
    private FileChooser fileChooser;
    private File file;

    /*----CONSTRUCTORS----*/
    public FileMenu(Stage stage, Canvas canvas)
    {
        super(stage, canvas);
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP Files", "*.bmp"));
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
        String format = chooseFormat(file);

        try
        {
            Image snapshot = getCanvas().snapshot(null, null);

            if (!ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), format, file)) //throws exception if it can't write in that format
                throw new Exception("failed to locate suitable writer");
        }
        catch (Exception e)
        {
            System.out.println("Failed to save image: " + e);
        }
    }

    //saves as png by default
    private String chooseFormat(File file)
    {
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
            return "jpg";
        else if (fileName.endsWith(".bmp"))
            return "bmp";
        else
            return "png";
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
