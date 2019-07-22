package com.hdsx.lh.maptiles.core;

public class TileCoord
{
    public TileCoord(int zoom, double x, double y,int index)
    {
        Zoom = zoom;
        X = x;
        Y = y;
        Index = index;
    }

    public int Index;
    public int Zoom;
    public double X;
    public double Y;
}