package com.hdsx.lh.maptiles.core;

import java.util.ArrayList;
import java.util.List;

public class TmsTileGrid implements ITileGrid {
    protected double[] resolutions;
    protected Extent extent;
    protected Coordinate origin;
    protected Size tileSize;
    protected List<Extent> tileRanges = null;
    protected double totalTile = 0;

    public TmsTileGrid(double[] resolutions, Extent extent, Coordinate origin, Size tileSize) {
        this.resolutions = resolutions;
        this.extent = extent;
        this.origin = origin;
        this.tileSize = tileSize;
        this.tileRanges = CalculateTileRanges();
    }

    protected List<Extent> CalculateTileRanges() {
        List<Extent> result = new ArrayList<>();
        for (int i = 0, len = resolutions.length; i < len; i++) {
            Coordinate leftBottomTileCoord = GetTileCoordByXYAndZoom(i, this.extent.GetLeftBottom(), false);
            Coordinate rightTopTileCoord = GetTileCoordByXYAndZoom(i, this.extent.GetTopRight(), true);
            result.add(new Extent(leftBottomTileCoord, rightTopTileCoord));
            totalTile += (rightTopTileCoord.X - leftBottomTileCoord.X + 1) * (rightTopTileCoord.Y - leftBottomTileCoord.Y + 1);
        }
        return result;
    }

    protected Coordinate GetTileCoordByXYAndZoom(int zoom, Coordinate coord, boolean reverseIntersectionPolicy) {
        double adjustX = reverseIntersectionPolicy ? 0.5 : 0.0;
        double adjustY = reverseIntersectionPolicy ? 0.0 : 0.5;
        double resolution = this.resolutions[zoom];
        double xFromOrigin = Math.floor((coord.X - this.origin.X) / resolution + adjustX);
        double yFromOrigin = Math.floor((coord.Y - this.origin.Y) / resolution + adjustY);
        double tileCoordX = xFromOrigin / this.tileSize.Width;
        double tileCoordY = yFromOrigin / this.tileSize.Height;
        if (reverseIntersectionPolicy) {
            tileCoordX = Math.ceil(tileCoordX) - 1;
            tileCoordY = Math.ceil(tileCoordY) - 1;
        } else {
            tileCoordX = Math.floor(tileCoordX);
            tileCoordY = Math.floor(tileCoordY);
        }
        return new Coordinate(tileCoordX, tileCoordY);
    }

    @Override
    public Extent GetTileExtent(TileCoord tileCoord) {
        Coordinate origin = this.origin;
        double resolution = this.resolutions[tileCoord.Zoom];
        Size tileSize = this.tileSize;
        double minX = origin.X + tileCoord.X * tileSize.Width * resolution;
        double minY = origin.Y + tileCoord.Y * tileSize.Height * resolution;
        double maxX = minX + tileSize.Width * resolution;
        double maxY = minY + tileSize.Height * resolution;
        return new Extent(new double[]{minX, minY, maxX, maxY});
    }

    public Size getTileSize() {
        return tileSize;
    }

    public List<Extent> getTileRanges() {
        return tileRanges;
    }

    public double get_totalTile() {
        return totalTile;
    }
}
