package org.example;

import javafx.scene.image.Image;

import java.util.Stack; //used stack instead of deque because stack is thread safe

/**
 * The CanvasStateManager class controls the undo/redo feature using an undo stack and a redo stack
 */
public class CanvasStateManager{
    Stack<Image> undo;
    Stack<Image> redo;

    CanvasControl screen;

    /**
     * Creates a CanvasStateController
     *
     * @param screen the canvas holder
     */
    public CanvasStateManager(CanvasControl screen)
    {
        this.screen = screen;

        undo = new Stack<>();
        redo = new Stack<>();

        undo.push(screen.getSnapshot());
    }

    /**
     * Goes back a state if the undo stack consists of more than the blank state
     */
    public void undo()
    {
        if (undo.size() > 1) //will always need blank state at the bottom
        {
            redo.push(undo.pop());

            screen.clearAll();
            screen.drawImage(undo.peek());
        }
    }

    /**
     * Goes forward a state if the redo stack is not empty
     */
    public void redo()
    {
        if (!redo.isEmpty())
        {
            undo.push(redo.peek());

            screen.clearAll();
            screen.drawImage(redo.pop());
        }
    }

    /**
     * Adds a state to the undo stack and clears the redo stack
     */
    public void addState()
    {
        //TODO: fix bug of eraser not being able to erase because the states are images on the image layer, also maybe just save the drawing layer anyway
        undo.push(screen.getSnapshot());
        redo.clear(); //reset redo stack to remove ambiguity if we return to that state via undo again
    }
}
