package com.hdsx.lh.maptiles.core;

public class Extent
{
    public double MinX;
    public double MinY;
    public double MaxX;
    public double MaxY;

    public Extent(double[] extent)
    {
        if (extent == null || extent.length < 4)
        {
            throw new IllegalArgumentException("extent must be array [minx,miny,maxx,maxy]");
        }
        MinX = extent[0];
        MinY = extent[1];
        MaxX = extent[2];
        MaxY = extent[3];
    }

    public Extent(Coordinate leftBottom, Coordinate topRight)
    {
        if (leftBottom != null && topRight != null)
        {
            MinX = leftBottom.X;
            MinY = leftBottom.Y;
            MaxX = topRight.X;
            MaxY = topRight.Y;
        }
    }

    public Extent(double minX, double minY, double maxX, double maxY)
    {
        MinX = minX;
        MinY = minY;
        MaxX = maxX;
        MaxY = maxY;
    }

    public Coordinate GetLeftBottom()
    {
        return new Coordinate(MinX, MinY);
    }

    public Coordinate GetTopRight()
    {
        return new Coordinate(MaxX, MaxY);
    }

    public Coordinate GetTopLeft()
    {
        return new Coordinate(MinX, MaxY);
    }

    public Coordinate GetRightBottom()
    {
        return new Coordinate(MaxX, MinY);
    }

    @Override
    public String toString() {
        return "Extent{" +
                "MinX=" + MinX +
                ", MinY=" + MinY +
                ", MaxX=" + MaxX +
                ", MaxY=" + MaxY +
                '}';
    }



}