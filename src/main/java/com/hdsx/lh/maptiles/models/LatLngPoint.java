package com.hdsx.lh.maptiles.models;

import com.vividsolutions.jts.geom.Geometry;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 *  经纬度坐标
 * */
public class LatLngPoint implements Serializable {
    private static final long serialVersionUID = -3774161836947549433L;

//    经度
    public double Lat;
//纬度
    public double Lng;

    public LatLngPoint(double lat, double lng)
    {
        Lat = lat;
        Lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LatLngPoint)) {
            return false;
        }
        LatLngPoint that = (LatLngPoint) o;
        return Double.compare(that.Lat, Lat) == 0 &&
                Double.compare(that.Lng, Lng) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Lat, Lng);
    }

    @Override
    public String toString() {
        return "LatLngPoint{" +
                "Lat=" + Lat +
                ", Lng=" + Lng +
                '}';
    }
}
