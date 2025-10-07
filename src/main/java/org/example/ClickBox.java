package org.example;

import javafx.geometry.Point2D;

public class ClickBox {
    private Point2D firstPoint;
    private Point2D secondPoint;
    private Point2D origin;

    double originX;
    double originY;

    private double width;
    private double height;

    private boolean isSquare = false;

    public ClickBox() {}
    public ClickBox(Point2D firstPoint, Point2D secondPoint)
    {
        update(firstPoint, secondPoint);
    }

    public Point2D getOrigin() {return origin;}
    public double getWidth() {return width;}
    public double getHeight() {return height;}

    public void update(Point2D firstPoint, Point2D secondPoint)
    {
        //assume the first click is the origin (top-left-most point)
        originX = firstPoint.getX();
        originY = firstPoint.getY();

        width = Math.abs(firstPoint.getX() - secondPoint.getX());
        height = Math.abs(firstPoint.getY() - secondPoint.getY());

        if (secondPoint.getX() < firstPoint.getX())
            originX = secondPoint.getX();
        if (secondPoint.getY() < firstPoint.getY())
            originY = secondPoint.getY();

        //make first point the origin
        this.origin = new Point2D(originX, originY);
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;

        if (isSquare)
            makeSquare();
    }

    public void makeSquare()
    {
        isSquare = true;

        if (origin != null)
        {
            width = height = Math.min(width, height);

            if (secondPoint.getX() < firstPoint.getX())
                originX = firstPoint.getX() - height;
            if (secondPoint.getY() < firstPoint.getY())
                originY = firstPoint.getY() - height;

            origin = new Point2D(originX, originY);
        }
    }
}
