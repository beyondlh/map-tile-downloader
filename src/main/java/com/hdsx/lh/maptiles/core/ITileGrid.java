package com.hdsx.lh.maptiles.core;

import java.util.List;

public interface ITileGrid {
    List<Extent> TileRanges = null;
    Size TileSize = null;
    double TotalTile = 0;

    Extent GetTileExtent(TileCoord tileCoord);


}