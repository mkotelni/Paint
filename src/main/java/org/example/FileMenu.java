package org.example;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class FileMenu extends Screen{
    private FileChooser fileChooser;
    private File file;

    /*----CONSTRUCTORS----*/

    /**
     * FileMenu constructor that sets image filters to PNG, JPG, and BMP
     *
     * @param stage The current stage
     * @param canvas The current canvas
     */
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

    /**
     * writes what's on the canvas as an image to a given file path
     *
     * @param file Image file path
     */
    //writes whatever's on screen to a given file path
    private void writeImage(File file) //precondition: file != null
    {
        Image snapshot = getCanvas().snapshot(null, null);
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);
        String format = chooseFormat(file);

        if (format.equals("jpg") || format.equals("bmp")) // jpg/bmp has no alpha support, won't save
            image = stripAlpha(snapshot);

        try
        {
            if (!ImageIO.write(image, format, file)) //throws exception if it can't write in that format
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

    /**
     * Strips an image of its alpha channel
     *
     * @param image The image that needs to be stripped of alpha
     * @return The image without its alpha channel
     */
    //used if we need to convert to a file type that doesn't support alpha (transparency)
    private BufferedImage stripAlpha(Image image)
    {
        BufferedImage snapshot = SwingFXUtils.fromFXImage(image, null);

        BufferedImage strippedImage = new BufferedImage(snapshot.getWidth(),
                                                        snapshot.getHeight(),
                                                        BufferedImage.TYPE_INT_RGB);

        Graphics2D g = strippedImage.createGraphics();
        g.drawImage(strippedImage, 0, 0, Color.WHITE, null);
        g.dispose();

        return strippedImage;
    }

    /*-----BUTTON ACTIONS------*/

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
