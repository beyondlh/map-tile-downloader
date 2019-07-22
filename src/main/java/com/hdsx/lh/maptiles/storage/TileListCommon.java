package com.hdsx.lh.maptiles.storage;

public abstract class TileListCommon implements TileList {
    private int[] downloadZoomLevels;

    /**
     * @param lat
     * @param zoomLevel
     * @return tileY
     */
    protected final int calculateTileY(double lat, int zoomLevel)
    {
        if (lat < Constants.MIN_LAT)
        {
            lat = Constants.MIN_LAT;
        }
        if (lat > Constants.MAX_LAT)
        {
            lat = Constants.MAX_LAT;
        }
        int y = (int) Math.floor((1 - Math.log(Math.tan(lat * Math.PI / 180) + 1 / Math.cos(lat * Math.PI / 180)) / Math.PI) / 2 * (1 << zoomLevel));
        return y;
    }

    /**
     * @param lon
     * @param zoomLevel
     * @return tileX
     */
    protected final int calculateTileX(double lon, int zoomLevel)
    {
        if (lon < Constants.MIN_LON)
        {
            lon = Constants.MIN_LON;
        }
        if (lon > Constants.MAX_LON)
        {
            lon = Constants.MAX_LON;
        }

        int x = (int) Math.floor((lon + 180) / 360 * (1 << zoomLevel));
        return x;
    }

    /**
     * Getter for downloadZoomLevel
     * @return the downloadZoomLevel
     */
    public final int[] getDownloadZoomLevels()
    {
        return downloadZoomLevels.clone();
    }

    /**
     * Setter for downloadZoomLevel
     * @param downloadZoomLevel the downloadZoomLevel to set
     */
    public final void setDownloadZoomLevels(int[] downloadZoomLevel)
    {
        downloadZoomLevels = downloadZoomLevel.clone();
    }

}
