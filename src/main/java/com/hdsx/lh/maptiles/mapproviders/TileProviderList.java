package com.hdsx.lh.maptiles.mapproviders;

public class TileProviderList
{
    private TileProvider[] tileProviders;

    /**
     * Sets up the tileProviderList
     */
    public TileProviderList() {
        super();
        tileProviders = new TileProvider[] {
                new GenericTileProvider("Cyclemap (CloudMade)", "http://c.andy.sandbox.cloudmade.com/tiles/cycle/"),
                new GenericTileProvider("Cyclemap (Thunderflames)", "http://thunderflames.org/tiles/cycle/"),
                new GenericTileProvider("OpenStreetBrowser (Europe)", "http://www.openstreetbrowser.org/tiles/base/"),
                new GenericTileProvider("OpenPisteMap", "http://openpistemap.org/tiles/contours/"),
                new GenericTileProvider("Maplint", "http://tah.openstreetmap.org/Tiles/maplint/"),
                new GenericTileProvider("CloudMade Web style", "http://tile.cloudmade.com/8bafab36916b5ce6b4395ede3cb9ddea/1/256/"),
                new GenericTileProvider("CloudMade Mobile style", "http://tile.cloudmade.com/8bafab36916b5ce6b4395ede3cb9ddea/2/256/"),
                new GenericTileProvider("CloudMade NoNames style", "http://tile.cloudmade.com/8bafab36916b5ce6b4395ede3cb9ddea/3/256/"),
                new GenericTileProvider("CloudMade Night style", "http://tile.cloudmade.com/BC9A493B41014CAABB98F0471D759707/999/256/")
        };
    }

    public TileProvider[] getTileProviderList()
    {
        return tileProviders.clone();
    }
}
