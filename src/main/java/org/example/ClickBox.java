package org.example;

import javafx.geometry.Point2D;

public class ClickBox {
    private Point2D firstPoint;
    private Point2D secondPoint;
    private Point2D origin;
    private Point2D center;

    private double originX;
    private double originY;
    private double centerX;
    private double centerY;

    private double width;
    private double height;

    private boolean isSquare = false;

    public ClickBox() {}
    public ClickBox(Point2D firstPoint, Point2D secondPoint)
    {
        update(firstPoint, secondPoint);
    }

    public Point2D getOrigin() {return origin;}
    public Point2D getCenter() {return center;}
    public double getWidth() {return width;}
    public double getHeight() {return height;}

    public void update(Point2D firstPoint, Point2D secondPoint)
    {
        //assume the first click is the origin (top-left-most point)
        originX = firstPoint.getX();
        originY = firstPoint.getY();

        width = Math.abs(firstPoint.getX() - secondPoint.getX());
        height = Math.abs(firstPoint.getY() - secondPoint.getY());

        //move origin coords to correct location if needed
        if (secondPoint.getX() < firstPoint.getX())
            originX = secondPoint.getX();
        if (secondPoint.getY() < firstPoint.getY())
            originY = secondPoint.getY();

        centerX = originX + width / 2;
        centerY = originY + height / 2;

        this.origin = new Point2D(originX, originY); //update origin
        this.center = new Point2D(centerX, centerY);
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

            //move origin location to its proper place
            if (secondPoint.getX() < firstPoint.getX())
                originX = firstPoint.getX() - width;
            if (secondPoint.getY() < firstPoint.getY())
                originY = firstPoint.getY() - height;

            //update center coords to reflect changes to origin
            centerX = originX + (width / 2);
            centerY = originY + (height / 2);

            origin = new Point2D(originX, originY);
            center = new Point2D(centerX, centerY);
        }
    }

    public void makeRectangular()
    {
        isSquare = false;
        update(firstPoint, secondPoint);
    }
}
