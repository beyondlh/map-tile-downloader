package com.hdsx.lh.maptiles.core;

public class Coordinate
{
    public Coordinate(double[] coord)
    {
        if (coord == null || coord.length < 2)
        {
            throw new IllegalArgumentException("extent must be array [x,y]");
        }
        X = coord[0];
        Y = coord[1];
    }

    public Coordinate(double x, double y)
    {
        X = x;
        Y = y;
    }

    public double X;
    public double Y;

}