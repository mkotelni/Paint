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
        if (!undo.empty()) {
            redo.push(undo.peek());
            screen.clearAll();
            screen.drawImage(undo.pop());
        }
    }




}
