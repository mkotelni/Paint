package org.example;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
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
    @FXML
    private CheckBox dashBox;
    @FXML
    private Spinner<Integer> numPoints;
    @FXML
    private TextField textInput;

    private Stage stage;

    private Screen screen;
    private FileMenu fileMenu;
    private AlertWindow alertWindow;

    //important to note YOU CANNOT USE 2 TOOLS AT ONCE!!!
    private Tool currentTool;
    private SizeableTool currentSizeableTool;

    /*----CONSTRUCTORS----*/
    public PaintController(Stage stage)
    {
        this.stage = stage;
    }

    /*-----SETTERS-----*/

    /**
     * This method sets and installs a given Tool
     *
     * @param tool A Tool object for tools that don't have a size
     */
    private void setTool(Tool tool)
    {
        currentTool = tool;

        clearMouseEvents(canvas);
        tool.install(canvas, screen.getGraphics(), colorPicker); //screen will be the one with the graphics (is that a good idea??? idk)
    }

    /**
     * This method sets and installs a given SizeableTool
     *
     * @param sizeableTool A Sizeable Tool object for tools that have a size
     */
    private void setSizeableTool(SizeableTool sizeableTool)
    {
        currentSizeableTool = sizeableTool;

        clearMouseEvents(canvas);
        sizeableTool.install(canvas, screen.getGraphics(), colorPicker);
        onBrushSize();
    }

    /*-----INITIALIZE-----*/
    @FXML
    private void initialize()
    {
        //TODO: fix bug where resizing window clears part of canvas that became hidden from resize
        // fix bug by either setting canvas to max screen size or by increasing size when window gets bigger but not letting it get smaller
        canvas.widthProperty().bind(borderPane.widthProperty());
        canvas.heightProperty().bind(borderPane.heightProperty());

        screen = new Screen(stage, canvas);
        fileMenu = new FileMenu(stage, canvas);
        alertWindow = new AlertWindow();

        //smart save
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            alertWindow.handleExit(fileMenu);
        });

        numPoints.valueProperty().addListener((obs, oldValue, newValue) -> {
            onPolygon();
        });

        setSizeableTool(new BrushTool()); //brush is selected automatically on startup
    }

    /*-----HELPER FUNCTIONS-----*/

    /**
     * Clears all mouse events used by paint tools
     *
     * @param canvas The current canvas used for drawing
     */
    public static void clearMouseEvents(Canvas canvas)
    {
        canvas.setOnMousePressed(null);
        canvas.setOnMouseReleased(null);
        canvas.setOnMouseClicked(null);
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseMoved(null);
        canvas.setOnMouseEntered(null);
        canvas.setOnMouseExited(null);
    }

    /**
     * Checks to see if lines drawn should be dashed, scales with width, with dash length by a factor of 10 and dash gap by a factor of 5
     */
    public void isDashMode()
    {
        double size = Double.parseDouble(brushSize.getText());

        if (dashBox.isSelected())
            screen.getGraphics().setLineDashes(size * 10, size * 5); //length of 10x, gap of 5x
        else
            screen.getGraphics().setLineDashes(0);
    }

    /*-----FILE MENU ACTIONS-----*/
    public void onSave()
    {
        fileMenu.save();
    }

    public void onSaveAs()
    {
        fileMenu.saveAs();
    }

    public void onLoadImage()
    {
        fileMenu.loadImage();
    }

    /*-----DRAW MENU ACTIONS-----*/
    public void onStraightLine()
    {
        setSizeableTool(new LineTool());
    }

    public void onSquare()
    {
        setSizeableTool(new ShapeTool(Shape.SQUARE));
    } //SQUARE_TOOL

    public void onRectangle()
    {
        setSizeableTool(new ShapeTool(Shape.RECTANGLE));
    } //RECTANGLE_TOOL

    public void onCircle()
    {
        setSizeableTool(new ShapeTool(Shape.CIRCLE));
    } //CIRCLE_TOOL

    public void onEllipse()
    {
        setSizeableTool(new ShapeTool(Shape.ELLIPSE));
    } //ELLIPSE_TOOL

    public void onTriangle()
    {
        setSizeableTool(new PolygonTool());
    } //default polygon is a triangle

    public void onPolygon(){setSizeableTool(new PolygonTool(numPoints.getValue()));}

    /*-----HELP MENU ACTIONS-----*/
    public void onHelp()
    {
        alertWindow.showHelp();
    }

    public void onStar()
    {
        setSizeableTool(new LineTool(Shape.STAR));
    }

    public void onAbout()
    {
        alertWindow.showAbout();
    }

    /*-----TOOL MENU ACTIONS-----*/
    public void onBrush()
    {
        setSizeableTool(new BrushTool());
    }

    public void onDropper()
    {
        setTool(new DropperTool(currentSizeableTool));
    }

    public void onEraser()
    {
        setSizeableTool(new EraserTool());
    }

    public void onAddText()
    {
        setTool(new TextTool(textInput.getText()));
    }

    //RESIZE BUTTON
    public void onResize() //TODO: make resize UI look better
    {
        if (fileMenu.getFile() != null) {
            double width = Double.parseDouble(imageWidth.getText());
            double height = Double.parseDouble(imageHeight.getText());

            screen.setImage(fileMenu.getImage());
            screen.drawImage(width, height);
        }
    }

    /*-----TEXTFIELD ACTIONS-----*/
    public void onBrushSize()
    {
        currentSizeableTool.setSize(Double.parseDouble(brushSize.getText()));
        isDashMode();
    }
}
