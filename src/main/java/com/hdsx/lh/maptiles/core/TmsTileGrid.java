package com.hdsx.lh.maptiles.core;

import java.util.*;

public class TmsTileGrid implements ITileGrid {
	protected double[] _resolutions;
	protected Extent _extent;
	protected Coordinate _origin;
	protected Size _tileSize;
	protected ArrayList<Extent> _tileRanges = null;
	protected double _totalTile = 0;

	public TmsTileGrid(double[] resolutions, Extent extent, Coordinate origin, Size tileSize) {
		this._resolutions = resolutions;
		this._extent = extent;
		this._origin = origin;
		this._tileSize = tileSize;
		this._tileRanges = CalculateTileRanges();
	}

	protected ArrayList<Extent> CalculateTileRanges() {
		ArrayList<Extent> result = new ArrayList<Extent>();
		for (int i = 0, len = _resolutions.length; i < len; i++)
		{
			Coordinate leftBottomTileCoord = GetTileCoordByXYAndZoom(i, this._extent.GetLeftBottom(), false);
			Coordinate rightTopTileCoord = GetTileCoordByXYAndZoom(i, this._extent.GetTopRight(), true);
			result.add(new Extent(leftBottomTileCoord, rightTopTileCoord));
			_totalTile += (rightTopTileCoord.X - leftBottomTileCoord.X + 1) * (rightTopTileCoord.Y - leftBottomTileCoord.Y + 1);
		}
		return result;
	}

	protected Coordinate GetTileCoordByXYAndZoom(int zoom, Coordinate coord, boolean reverseIntersectionPolicy) {
		double adjustX = reverseIntersectionPolicy ? 0.5 : 0.0;
		double adjustY = reverseIntersectionPolicy ? 0.0 : 0.5;
		double resolution = this._resolutions[zoom];
		double xFromOrigin = Math.floor((coord.X - this._origin.X) / resolution + adjustX);
		double yFromOrigin = Math.floor((coord.Y - this._origin.Y) / resolution + adjustY);
		double tileCoordX = xFromOrigin / this._tileSize.Width;
		double tileCoordY = yFromOrigin / this._tileSize.Height;
		if (reverseIntersectionPolicy) {
			tileCoordX = Math.ceil(tileCoordX) - 1;
			tileCoordY = Math.ceil(tileCoordY) - 1;
		} else {
			tileCoordX = Math.floor(tileCoordX);
			tileCoordY = Math.floor(tileCoordY);
		}
		return new Coordinate(tileCoordX, tileCoordY);
	}

	@Override
	public final Extent GetTileExtent(TileCoord tileCoord) {
		Coordinate origin = this._origin;
		double resolution = this._resolutions[tileCoord.Zoom];
		Size tileSize = this._tileSize;
		double minX = origin.X + tileCoord.X * tileSize.Width * resolution;
		double minY = origin.Y + tileCoord.Y * tileSize.Height * resolution;
		double maxX = minX + tileSize.Width * resolution;
		double maxY = minY + tileSize.Height * resolution;
		return new Extent(new double[] {minX, minY, maxX, maxY});
	}

	@Override
	public final ArrayList<Extent> getTileRanges() {
		return _tileRanges;
	}

	@Override
	public final double getTotalTile() {
		return _totalTile;
	}

	@Override
	public final Size getTileSize() {
		return _tileSize;
	}
}