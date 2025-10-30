package org.example;

import javafx.scene.image.Image;

import java.util.Stack; //used stack instead of deque because stack is thread safe

public class CanvasStateManager{
    Stack<Image> undo;
    Stack<Image> redo;

    CanvasControl screen;

    public CanvasStateManager(CanvasControl screen)
    {
        this.screen = screen;

        undo = new Stack<>();
        redo = new Stack<>();

        undo.push(screen.getSnapshot());
    }

    public void undo()
    {
        if (undo.size() > 1) //will always need blank state at the bottom
        {
            redo.push(undo.pop());

            screen.clearAll();
            screen.drawImage(undo.peek());
        }

        System.out.println("undo:" + undo.size()); //TEST
    }

    public void redo()
    {
        if (!redo.isEmpty())
        {
            undo.push(redo.peek());

            screen.clearAll();
            screen.drawImage(redo.pop());
        }

        System.out.println("redo:" + redo.size()); //TEST
    }

    public void addState()
    {
        undo.push(screen.getSnapshot());
        redo.clear(); //reset redo stack to remove ambiguity if we return to that state via undo again
    }
}
