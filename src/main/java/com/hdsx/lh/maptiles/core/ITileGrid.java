package com.hdsx.lh.maptiles.core;

import java.util.*;

public interface ITileGrid
{
	ArrayList<Extent> getTileRanges();
	Size getTileSize();
	double getTotalTile();

	Extent GetTileExtent(TileCoord tileCoord);
}