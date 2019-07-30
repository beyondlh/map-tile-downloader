package com.hdsx.lh.maptiles.mapproviders;

import com.hdsx.lh.maptiles.config.MapConfig;
import com.hdsx.lh.maptiles.core.*;

import java.util.ArrayList;
import java.util.Map;

/** 
 较通用的，基于TMS规则的XYZ瓦片下载
*/
public class TmsSourceProvider implements ISourceProvider
{
	protected ITileGrid _tileGrid = null;
	protected String _url = null;
	protected int _offsetZoom = 0;
	protected Map<String, Object> _paras = null;

	public TmsSourceProvider(MapConfig config)
	{
		Size tileSize = new Size(config.getTileSize());
		Extent extent = new Extent(config.getExtent());
		Coordinate origin = origin = new Coordinate(config.getOrigin());

		_tileGrid = CreateTileGrid(config.getResolutions(), extent, origin, tileSize);
		_url = config.getUrl();
		_paras = config.getUrlParas();
		_offsetZoom = config.getOffsetZoom();
	}

	@Override
	public final ITileGrid getTileGrid()
	{
		return _tileGrid;
	}

	@Override
    public String getRequestUrl(TileCoord tileCoord) {
		//http://webrd01.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x=" + x + "&y=" + y + "&z=" + z
		String url = this._url;
		url = url.replace("{z}", String.valueOf((_offsetZoom + tileCoord.Zoom)));
		url = url.replace("{x}", String.valueOf(Math.abs(tileCoord.X)));
		url = url.replace("{y}", String.valueOf(Math.abs(tileCoord.Y)));
		return url;
	}

	@Override
	public void EnumerateTileRange(TileCoord beginTile, TileCallBack getTileCallback) {

		ArrayList<Extent> fullTileRange = _tileGrid.getTileRanges();

		int minZoom = 0, index = 0;
		double minX = fullTileRange.get(minZoom).MinX;
		double minY = fullTileRange.get(minZoom).MinY;
		if (beginTile != null)
		{
			minZoom = beginTile.Zoom; //从失败的那一级别开始下载。
			minX = beginTile.X;
			minY = beginTile.Y;
		}


		for (double x = minX; x <= fullTileRange.get(minZoom).MaxX; ++x)
		{
			for (double y = minY; y <= fullTileRange.get(minZoom).MaxY; ++y)
			{
				++index;
				TileCoord tile = new TileCoord(minZoom, x, y, index);
				try {
					getTileCallback.call(tile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		for (int z = minZoom + 1; z < fullTileRange.size(); z++) {
			//for (double x = minX; x <= fullTileRange[z].MaxX; ++x)
			for (double x = fullTileRange.get(z).MinX; x <= fullTileRange.get(z).MaxX; ++x) {
				//for (double y = minY; y <= fullTileRange[z].MaxY; ++y)
				for (double y = fullTileRange.get(z).MinY; y <= fullTileRange.get(z).MaxY; ++y)
				{
					++index;
					TileCoord tile = new TileCoord(z, x, y, index);
					getTileCallback.call(tile);
				}
			}
		}
	}


	@Override
	public OutputTile getOutputTile(TileCoord input, int zoomOffset) {
		double x = input.X, y = input.Y;
		if (input.X < 0)
		{
			x = Math.abs(input.X);
		}
		if (input.Y < 0)
		{
			y = Math.abs(input.Y);
		}
		return new OutputTile(String.valueOf((input.Zoom + zoomOffset)), String.valueOf(x), String.valueOf(y));
	}

	protected ITileGrid CreateTileGrid(double[] resolutions, Extent extent, Coordinate origin, Size tileSize) {
		return new TmsTileGrid(resolutions, extent, origin, tileSize);
	}
}