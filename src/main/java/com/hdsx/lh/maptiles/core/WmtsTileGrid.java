package com.hdsx.lh.maptiles.core;

import java.util.*;

public class WmtsTileGrid extends TmsTileGrid {
	public WmtsTileGrid(double[] resolutions, Extent extent, Coordinate origin, Size tileSize) {
		super(resolutions, extent, origin, tileSize);

	}

	@Override
	protected ArrayList<Extent> CalculateTileRanges() {
		ArrayList<Extent> result = new ArrayList<Extent>();
		for (int i = 0, len = _resolutions.length; i < len; i++)
		{
			Coordinate topLeft = GetTileCoordByXYAndZoom(i, this._extent.GetTopLeft(), false);
			Coordinate rightBottom = GetTileCoordByXYAndZoom(i, this._extent.GetRightBottom(), true);
			Extent extent = new Extent(topLeft.X, (-1 * topLeft.Y - 1), rightBottom.X, (-1 * rightBottom.Y - 1));
			result.add(extent);
			_totalTile += (extent.MaxX - extent.MinX + 1) * (extent.MaxY - extent.MinY + 1);
		}
		return result;
	}
}