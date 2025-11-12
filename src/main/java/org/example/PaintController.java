package org.example;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The PaintController class controls actions performed by the UI elements
 */
public class PaintController {
    @FXML
    BorderPane borderPane;
    @FXML
    StackPane canvasStack;
    @FXML
    private Canvas imageCanvas;
    @FXML
    private Canvas drawingCanvas;
    @FXML
    private Canvas previewCanvas;
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
    private Spinner<Integer> numPointsPolygon;
    @FXML
    private Spinner<Integer> numPointsStar;
    @FXML
    private TextField textInput;
    @FXML
    private CheckBox irregularCheck;

    private Stage stage;

    private CanvasControl screen;
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
     * Sets and installs a given Tool
     *
     * @param tool A Tool object for tools that don't have a size
     */
    private void setTool(Tool tool)
    {
        currentTool = tool;

        //set up canvas
        clearMouseEvents(screen.getDrawingCanvas());
        drawingCanvas.toFront(); //assume the tool doesn't have/need a live preview mode

        //set up tool
        tool.install(screen, colorPicker);
    }

    /**
     * Sets and installs a given SizeableTool
     *
     * @param sizeableTool A Sizeable Tool object for tools that have a size
     */
    private void setSizeableTool(SizeableTool sizeableTool)
    {
        currentSizeableTool = sizeableTool;

        //set up canvases
        clearMouseEvents(screen.getDrawingCanvas());
        clearMouseEvents(screen.getPreviewCanvas());
        previewCanvas.toFront(); //assume the tool has a live preview mode
                                 //editable canvas changes to drawingCanvas in tool install method if tool doesn't have a preview mode
        //set up tool
        sizeableTool.install(screen, colorPicker);
        onBrushSize();
    }

    /*-----INITIALIZE-----*/
    @FXML
    private void initialize()
    {
        //initialize menu objects
        screen = new CanvasControl(stage, canvasStack, imageCanvas, drawingCanvas, previewCanvas);
        fileMenu = new FileMenu(screen);
        alertWindow = new AlertWindow();

        screen.getCanvasStateManager().addState(); //make the blank screen the 1st state REDUNDANT???

        //bind other canvas' dimensions to drawing canvas
        imageCanvas.widthProperty().bind(drawingCanvas.widthProperty());
        imageCanvas.heightProperty().bind(drawingCanvas.heightProperty());

        previewCanvas.widthProperty().bind(drawingCanvas.widthProperty());
        previewCanvas.heightProperty().bind(drawingCanvas.heightProperty());

        //smart save
        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            alertWindow.handleExit(fileMenu);
        });

        //update polygon tool whenever polygon point amount changes
        numPointsPolygon.valueProperty().addListener((obs, oldValue, newValue) -> {
            onPolygon();
        });

        //update star tool whenever star point amount changes
        numPointsStar.valueProperty().addListener((obs, oldValue, newValue) -> {
            onStar();
        });

        //brush is selected automatically on startup
        setSizeableTool(new BrushTool());

        //make edits on drawingCanvas possible, brush is the default tool and needs to edit on drawingCanvas
        drawingCanvas.toFront();
    }

    /*-----HELPER FUNCTIONS-----*/
    //TODO: think about moving helper function block to bottom of file
    /**
     * Clears all mouse events used by paint tools on a given canvas
     *
     * @param canvas A canvas layer
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
     * Checks to see if lines drawn should be dashed, scales with width.
     * Dash length scales by a factor of 10 and dash gap scales by a factor of 5
     */
    public void isDashMode()
    {
        double size = Double.parseDouble(brushSize.getText());

        if (dashBox.isSelected())
        {
            //set up dash mode for each canvas
            screen.getDrawingGraphics().setLineDashes(size * 10, size * 5); //length of 10x, gap of 5x
            screen.getPreviewGraphics().setLineDashes(size * 10, size * 5);
        }
        else
        {
            //cancel dash mode for each canvas
            screen.getDrawingGraphics().setLineDashes(0);
            screen.getPreviewGraphics().setLineDashes(0);
        }
    }

    /*-----FILE MENU ACTIONS-----*/
    public void onNewImage()
    {
        //TODO: don't display popup if blank canvas, just do
        if (alertWindow.handleNewCanvasWarning()) //confirm new canvas action
            screen.clearAll();
    }

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

    /*-----EDIT MENU ACTIONS-----*/
    public void onUndo(){screen.getCanvasStateManager().undo();}
    public void onRedo(){screen.getCanvasStateManager().redo();}

    /*-----DRAW MENU ACTIONS-----*/
    public void onStraightLine()
    {
        setSizeableTool(new LineTool());
    }

    public void onSquare()
    {
        setSizeableTool(new SquareTool());
    }

    public void onRectangle()
    {
        setSizeableTool(new RectangleTool());
    }

    public void onCircle()
    {
        setSizeableTool(new CircleTool());
    }

    public void onEllipse()
    {
        setSizeableTool(new EllipseTool());
    }

    public void onTriangle()
    {
        setSizeableTool(new TriangleTool());
    }

    public void onFan()
    {
        setSizeableTool(new FanTool());
    }

    public void onStar()
    {
        setSizeableTool(new StarTool(numPointsStar.getValue()));
    }

    public void onPolygon()
    {
        if (!irregularCheck.isSelected())
            setSizeableTool(new PolygonTool(numPointsPolygon.getValue()));
        else
            setSizeableTool(new IrregularPolygonTool(numPointsPolygon.getValue()));
    }

    /*-----HELP MENU ACTIONS-----*/
    public void onHelp()
    {
        alertWindow.showHelp();
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
        setSizeableTool(new TextTool(textInput.getText()));
    }

    //RESIZE BUTTON
    //TODO: make resize UI look better, maybe by adding a view menu (hint hint)
    //TODO: make a resize canvas option
    public void onResize()
    {
        double width = Double.parseDouble(imageWidth.getText());
        double height = Double.parseDouble(imageHeight.getText());

        if (fileMenu.getFile() != null)
            screen.drawImage(new Image(fileMenu.getFile().toURI().toString()), width, height); //screen already has loaded image through FileMenu.LoadImage()
        else
            screen.drawImage(screen.getImageCanvas().snapshot(null, null), width, height);
    }

    /*-----TEXTFIELD ACTIONS-----*/
    public void onBrushSize() //updates tool's size
    {
        currentSizeableTool.setSize(Double.parseDouble(brushSize.getText()));
        isDashMode(); //check if we're dashed just in case
    }
}
