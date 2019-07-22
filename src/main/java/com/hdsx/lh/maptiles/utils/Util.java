package com.hdsx.lh.maptiles.utils;

import com.hdsx.lh.maptiles.mapproviders.GenericTileProvider;
import com.hdsx.lh.maptiles.mapproviders.TileProvider;
import com.hdsx.lh.maptiles.mapproviders.TileProviderList;
import com.hdsx.lh.maptiles.storage.TileListUrlSquare;

import java.util.LinkedList;

public class Util {
    /**
     * Returns valid array of zoomlevels to download
     * @param selectedTileProvider
     * @param zoomLevelString
     * @return int[] zoomlevels
     */
    public static int[] getOutputZoomLevelArray(TileProvider selectedTileProvider, String zoomLevelString)
    {
        int minZoom = selectedTileProvider == null ? 0 : selectedTileProvider.getMinZoom();
        int maxZoom = selectedTileProvider == null ? 20 : selectedTileProvider.getMaxZoom();
        LinkedList<Integer> zoomLevels = new LinkedList<Integer>();
        for (String zoomLevel : zoomLevelString.split(","))
        {
            int z1, z2;
            int p = zoomLevel.indexOf('-');
            if( p > 0 ) {
                z1 = Integer.parseInt(zoomLevel.substring(0, p).trim());
                z2 = Integer.parseInt(zoomLevel.substring(p + 1).trim());
            } else {
                z1 = Integer.parseInt(zoomLevel.trim());
                z2 = z1;
            }
            for( int selectedZoom = z1; selectedZoom <= z2; selectedZoom ++ ) {
                if (selectedZoom <= maxZoom && selectedZoom >= minZoom)
                {
                    if (!zoomLevels.contains(selectedZoom))
                    {
                        zoomLevels.add(selectedZoom);
                    }
                }
            }
        }
        int[] parsedLevels = new int[zoomLevels.size()];
        for (int i = 0; i < zoomLevels.size(); i++)
        {
            parsedLevels[i] = zoomLevels.get(i);
        }
        return parsedLevels;
    }

    /**
     * @param tileServer
     * @return tileProvider
     */
    public static TileProvider getTileProvider(String tileServer) {
        TileProvider[] _tileProviders = new TileProviderList().getTileProviderList();
        for (TileProvider tileProvider : _tileProviders)
        {
            if (tileProvider.getName().equalsIgnoreCase(tileServer))
            {
                return tileProvider;
            }
        }
        return new GenericTileProvider(tileServer);
    }

    public static void parsePasteUrl(String url, TileListUrlSquare tileList) {
        //String pasteUrl = "http://www.openstreetmap.org/?lat=48.256&lon=13.0434&zoom=12&layers=0B0FT";
        if (url == null || url.length() == 0)
        {
            tileList.setLatitude(0);
            tileList.setLongitude(0);
            return;
        }

        try {
            int posLat = url.indexOf("lat=");
            String lat = url.substring(posLat);
            int posLon = url.indexOf("lon=");
            String lon = url.substring(posLon);

            int posAnd = lat.indexOf("&");
            lat = lat.substring(4, posAnd).replace(',', '.');
            posAnd = lon.indexOf("&");
            lon = lon.substring(4, posAnd).replace(',', '.');

            if( lat.length() > 0 && lon.length() > 0 ) {
                tileList.setLatitude(Double.parseDouble(lat));
                tileList.setLongitude(Double.parseDouble(lon));
            }
        } catch( NumberFormatException e ) {
            tileList.setLatitude(0);
            tileList.setLongitude(0);
        }
    }
}