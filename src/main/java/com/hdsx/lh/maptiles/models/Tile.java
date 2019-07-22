package com.hdsx.lh.maptiles.models;

import java.io.File;
import java.io.Serializable;
import java.util.Objects;

/**
 * 瓦片坐标
 */
public class Tile implements Serializable {
    private static final long serialVersionUID = 6624780672065961790L;
    protected TileSource tileSource;
    //瓦片大小
    public final static int TileSize = 256;
    /// <param name="x">瓦片X坐标</param>
    /// <param name="y">瓦片Y坐标</param>
    /// <param name="Z">层数</param>
    public long X;
    public long Y;
    public int Z;
    protected boolean loaded = false;
    protected boolean loading = false;
    public static final Tile Empty = new Tile();

    private Tile() {

    }


    /**
     * 构造方法
     */
    public Tile(long x, long y, int z) {
        X = x;
        Y = y;
        Z = z;
    }

    public static int[] getTileNumber(final double lat, final double lon, final int zoom) {
        int xtile = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
        int ytile = (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1 << zoom));

        if (xtile < 0) xtile = 0;
        if (xtile >= (1 << zoom)) xtile = ((1 << zoom) - 1);
        if (ytile < 0) ytile = 0;
        if (ytile >= (1 << zoom)) ytile = ((1 << zoom) - 1);

        int[] arr = {xtile, ytile};
        return arr;
    }

    /**
     * 是否是空点
     */
    public boolean IsEmpty() {
        if (X == 0 && Y == 0 & Z == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tile)) {
            return false;
        }
        Tile tilePoint = (Tile) o;
        return X == tilePoint.X &&
                Y == tilePoint.Y &&
                Z == tilePoint.Z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y, Z);
    }


    public long getX() {
        return X;
    }

    public void setX(long x) {
        X = x;
    }

    public long getY() {
        return Y;
    }

    public void setY(long y) {
        Y = y;
    }

    public int getZ() {
        return Z;
    }

    public void setZ(int z) {
        Z = z;
    }

    public TileSource getTileSource() {
        return tileSource;
    }

    public void setTileSource(TileSource tileSource) {
        this.tileSource = tileSource;
    }

    /**
     * Returns the relative path of a tile
     * @return relative tile-image path
     */
    public String getPath()
    {
        return Z + File.separator + X;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "X=" + X +
                ", Y=" + Y +
                ", Z=" + Z +
                '}';
    }
}
