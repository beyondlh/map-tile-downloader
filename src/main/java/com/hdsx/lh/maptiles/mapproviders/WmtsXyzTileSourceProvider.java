package com.hdsx.lh.maptiles.mapproviders;

import com.hdsx.lh.maptiles.config.MapConfig;
import com.hdsx.lh.maptiles.core.TileCoord;

/**
 一个较通用的，基于WMTS XYZ的瓦片请求
*/
public class WmtsXyzTileSourceProvider extends WmtsSourceProvider
{

	public WmtsXyzTileSourceProvider(MapConfig config)
	{
		super(config);
		this._offsetZoom = config.getOffsetZoom();
	}

	@Override
	public String getRequestUrl(TileCoord tileCoord)
	{
		String url = this._url;
		url = url.replace("{z}", String.valueOf(_offsetZoom + tileCoord.Zoom));
		url = url.replace("{y}", String.valueOf(tileCoord.Y));
		url = url.replace("{x}", String.valueOf(tileCoord.X));
		return url;
	}
}