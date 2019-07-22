package com.hdsx.lh.maptiles.storage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TileListUrlSquare extends TileListCommonBBox {
    private static final Logger log = Logger.getLogger(TileListUrlSquare.class.getName());
    private int radius; // radius in m
    private double latitude;
    private double longitude;

    public void calculateTileValuesXY()
    {

        log.log(Level.FINE, "calculate tile values for (UrlSquare:) lat {0}, lon {1}, radius {2}", new Object[]{latitude, longitude, radius});

        if (radius > 6370000 * 2 * 4)
        {
            radius = 6370000 * 2 * 4;
        }

        double minLat = latitude - 360 * (radius / Constants.EARTH_CIRC_POLE);
        double minLon = longitude - 360 * (radius / (Constants.EARTH_CIRC_EQUATOR * Math.cos(longitude * Math.PI / 180)));
        double maxLat = latitude + 360 * (radius / Constants.EARTH_CIRC_POLE);
        double maxLon = longitude + 360 * (radius / (Constants.EARTH_CIRC_EQUATOR * Math.cos(longitude * Math.PI / 180)));

        log.log(Level.FINE, "minLat={0}", minLat);
        log.log(Level.FINE, "minLon={0}", minLon);
        log.log(Level.FINE, "maxLat={0}", maxLat);
        log.log(Level.FINE, "maxLon={0}", maxLon);

        calculateTileValuesXY(minLat, minLon, maxLat, maxLon);

    }

    /**
     * Getter for radius
     * @return the radius
     */
    protected final int getRadius()
    {
        return radius;
    }

    /**
     * Setter for radius in meter
     * @param radius the radius to set
     */
    public final void setRadius(int radius)
    {
        this.radius = radius;
    }

    /**
     * Getter for latitude
     * @return the latitude
     */
    public final double getLatitude()
    {
        return latitude;
    }

    /**
     * Setter for latitude
     * @param latitude the latitude to set
     */
    public final void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    /**
     * Getter for longitude
     * @return the longitude
     */
    public final double getLongitude()
    {
        return longitude;
    }

    /**
     * Setter for longitude
     * @param longitude the longitude to set
     */
    public final void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

}
