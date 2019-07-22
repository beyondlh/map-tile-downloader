package com.hdsx.lh.maptiles.storage;

import com.hdsx.lh.maptiles.models.Tile;

import java.util.ArrayList;
import java.util.logging.Logger;

public class TileListCommonBBox extends TileListCommon {
    private static final Logger log = Logger.getLogger(TileListCommonBBox.class.getName());
    private int[] xTopLeft = new int[]{0};
    private int[] yTopLeft = new int[]{0};
    private int[] xBottomRight = new int[]{0};
    private int[] yBottomRight = new int[]{0};

    /**
     * @see com.hdsx.lh.maptiles.storage.TileList#getTileListToDownload()
     */
    @Override
    public ArrayList<Tile> getTileListToDownload() {
        ArrayList<Tile> tilesToDownload = new ArrayList<Tile>();

        for (int indexZoomLevel = 0; indexZoomLevel < getDownloadZoomLevels().length; indexZoomLevel++) {
            int zoom = getDownloadZoomLevels()[indexZoomLevel];

            int xStart = Math.min(xTopLeft[indexZoomLevel], xBottomRight[indexZoomLevel]);
            int xEnd = Math.max(xTopLeft[indexZoomLevel], xBottomRight[indexZoomLevel]);

            int yStart = Math.min(yTopLeft[indexZoomLevel], yBottomRight[indexZoomLevel]);
            int yEnd = Math.max(yTopLeft[indexZoomLevel], yBottomRight[indexZoomLevel]);

            for (int downloadTileXIndex = xStart; downloadTileXIndex <= xEnd; downloadTileXIndex++) {
                for (int downloadTileYIndex = yStart; downloadTileYIndex <= yEnd; downloadTileYIndex++) {
                    String urlPathToFile = zoom + "/" + downloadTileXIndex + "/" + downloadTileYIndex + ".png";

                    log.fine("add " + urlPathToFile + " to download list.");
                    tilesToDownload.add(new Tile(downloadTileXIndex, downloadTileYIndex, zoom));
                }
            }

        }

        log.fine("finished");

        return tilesToDownload;

    }

    /**
     * @param minLat
     * @param minLon
     * @param maxLat
     * @param maxLon
     */
    public void calculateTileValuesXY(double minLat, double minLon, double maxLat, double maxLon) {
        int zoomLevelSize = getDownloadZoomLevels().length;

        xTopLeft = new int[zoomLevelSize];
        yTopLeft = new int[zoomLevelSize];
        xBottomRight = new int[zoomLevelSize];
        yBottomRight = new int[zoomLevelSize];

        for (int indexZoomLevel = 0; indexZoomLevel < zoomLevelSize; indexZoomLevel++) {
            setXTopLeft(calculateTileX(minLon, getDownloadZoomLevels()[indexZoomLevel]), indexZoomLevel);
            setYTopLeft(calculateTileY(maxLat, getDownloadZoomLevels()[indexZoomLevel]), indexZoomLevel);
            setXBottomRight(calculateTileX(maxLon, getDownloadZoomLevels()[indexZoomLevel]), indexZoomLevel);
            setYBottomRight(calculateTileY(minLat, getDownloadZoomLevels()[indexZoomLevel]), indexZoomLevel);

            log.fine("XTopLeft=" + getXTopLeft()[indexZoomLevel]);
            log.fine("YTopLeft=" + getYTopLeft()[indexZoomLevel]);
            log.fine("XBottomRight=" + getXBottomRight()[indexZoomLevel]);
            log.fine("YBottomRight=" + getYBottomRight()[indexZoomLevel]);
        }

    }

    /**
     * Setter for topLeft
     *
     * @param value the xTopLeft to set
     */
    public final void initXTopLeft(int value) {
        xTopLeft = new int[getDownloadZoomLevels().length];
        for (int index = 0; index < getDownloadZoomLevels().length; index++) {
            xTopLeft[index] = value;

        }
    }

    /**
     * Setter for topLeft
     *
     * @param value the xTopLeft to set
     */
    public final void initYTopLeft(int value) {
        yTopLeft = new int[getDownloadZoomLevels().length];
        for (int index = 0; index < getDownloadZoomLevels().length; index++) {
            yTopLeft[index] = value;

        }
    }

    /**
     * Setter for BottomRight
     *
     * @param value the xBottomRight to set
     */
    public final void initXBottomRight(int value) {
        xBottomRight = new int[getDownloadZoomLevels().length];
        for (int index = 0; index < getDownloadZoomLevels().length; index++) {
            xBottomRight[index] = value;

        }
    }

    /**
     * Setter for BottomRight
     *
     * @param value the xBottomRight to set
     */
    public final void initYBottomRight(int value) {
        yBottomRight = new int[getDownloadZoomLevels().length];
        for (int index = 0; index < getDownloadZoomLevels().length; index++) {
            yBottomRight[index] = value;

        }
    }

    /**
     * Getter for xTopLeft
     *
     * @return the xTopLeft
     */
    public final int[] getXTopLeft() {
        return xTopLeft.clone();
    }

    /**
     * Setter for topLeft
     *
     * @param topLeft the xTopLeft to set
     * @param index
     */
    public final void setXTopLeft(int topLeft, int index) {
        xTopLeft[index] = topLeft;
    }

    /**
     * Getter for yTopLeft
     *
     * @return the yTopLeft
     */
    public final int[] getYTopLeft() {
        return yTopLeft.clone();
    }

    /**
     * Setter for topLeft
     *
     * @param topLeft the yTopLeft to set
     * @param index
     */
    public final void setYTopLeft(int topLeft, int index) {
        yTopLeft[index] = topLeft;
    }

    /**
     * Getter for xBottomRight
     *
     * @return the xBottomRight
     */
    public final int[] getXBottomRight() {
        return xBottomRight.clone();
    }

    /**
     * Setter for bottomRight
     *
     * @param bottomRight the xBottomRight to set
     * @param index
     */
    public final void setXBottomRight(int bottomRight, int index) {
        xBottomRight[index] = bottomRight;
    }

    /**
     * Getter for yBottomRight
     *
     * @return the yBottomRight
     */
    public final int[] getYBottomRight() {
        return yBottomRight.clone();
    }

    /**
     * Setter for bottomRight
     *
     * @param bottomRight the yBottomRight to set
     * @param index
     */
    public final void setYBottomRight(int bottomRight, int index) {
        yBottomRight[index] = bottomRight;
    }

    /**
     * @return tile count
     */
    public int getTileCount() {
        int count = 0;
        for (int indexZoomLevels = 0; indexZoomLevels < getDownloadZoomLevels().length; indexZoomLevels++) {
            // WTF?
            count += Integer.parseInt("" + (Math.abs(getXBottomRight()[indexZoomLevels] - getXTopLeft()[indexZoomLevels]) + 1) * (Math.abs(getYBottomRight()[indexZoomLevels] - getYTopLeft()[indexZoomLevels]) + 1));
        }

        return count;
    }

}