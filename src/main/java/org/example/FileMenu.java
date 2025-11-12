package org.example;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * The FileMenu class handles file related events such as opening saving image files.
 * Currently supported file types include PNG, JPG, and BMP.
 */
public class FileMenu{
    private FileChooser fileChooser;
    private File file;
    private CanvasControl screen;

    private AlertWindow alert;

    /*----CONSTRUCTORS----*/
    /**
     * FileMenu constructor that sets image filters to PNG, JPG, and BMP
     *
     * @param screen canvas holder
     */
    public FileMenu(CanvasControl screen)
    {
        this.screen = screen;
        alert = new AlertWindow();

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP Files", "*.bmp"));
    }

    /*----GETTERS----*/

    /**
     * Returns the current image file
     *
     * @return the image file
     */
    public File getFile() {return file;}
    public FileChooser getFileChooser() {return fileChooser;}

    /*----SETTERS----*/
    public void setFile(File file) {this.file = file;}
    public void setFileChooser(FileChooser fileChooser) {this.fileChooser = fileChooser;}


    /*----HELPER FUNCTIONS----*/

    /**
     * Writes what's on the canvas as an image to a given file path
     *
     * @param file the image file path
     */
    private void writeImage(File file, String previousFileName) //precondition: file != null
    {
        Image snapshot = screen.getSnapshot();
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);
        String format = chooseFormat(file);

        if (format.equals("jpg") || format.equals("bmp")) // jpg/bmp has no alpha support, won't save
        {
            if (previousFileName.endsWith(".png"))
                if (!alert.handleConversionWarning()) //if clicked cancel
                    return;

            image = stripAlpha(snapshot);
        }

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
     * @param image The image to be stripped of alpha
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
        g.drawImage(snapshot, 0, 0, Color.WHITE, null);
        g.dispose();

        return strippedImage;
    }

    /*-----BUTTON ACTIONS------*/

    /**
     * Saves what's onscreen to the current file path
     */
    public void save() {
        if (file != null)
            writeImage(file, file.getName().toLowerCase());
        else
            System.out.println("File was not chosen (file is null)");
    }

    /**
     * Saves what's onscreen to a new file
     */
    public void saveAs() {
        String previousFileFormat = "";
        if (file != null) //if there is a previous file format
            previousFileFormat = file.getName().toLowerCase(); //have to grab name before reassigning file later on

        fileChooser.setTitle("Save as");
        file = fileChooser.showSaveDialog(screen.getStage());

        if (file != null) //not the same file, check again
            writeImage(file, previousFileFormat);
    }

    /**
     * Allows the user to select and display an image file
     */
    public void loadImage()
    {
        fileChooser.setTitle("Select Image File");
        file = fileChooser.showOpenDialog(screen.getStage());

        if (file != null)
        {
            Image image = new Image(file.toURI().toString());
            screen.drawImage(image, image.getWidth(), image.getHeight());
        }
    }
}
