package com.hdsx.lh.maptiles.mapproviders;

import com.hdsx.lh.maptiles.core.ITileGrid;
import com.hdsx.lh.maptiles.core.OutputTile;
import com.hdsx.lh.maptiles.core.TileCoord;

public interface ISourceProvider {
    String getRequestUrl(TileCoord tileCoord);

    void EnumerateTileRange(TileCoord beginTile, TileCallBack getTileCallback);

    ITileGrid getTileGrid();

    OutputTile getOutputTile(TileCoord input, int zoomOffset);
}