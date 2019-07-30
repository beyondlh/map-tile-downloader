package com.hdsx.lh.maptiles.mapproviders;

import com.hdsx.lh.maptiles.config.MapConfig;
import com.hdsx.lh.maptiles.core.*;

import java.util.*;

/** 
 geoserver wmts或其他标准的WMTS（例如：天地图）
*/
public class WmtsSourceProvider extends TmsSourceProvider
{
	public WmtsSourceProvider(MapConfig config)
	{
		super(config);

	}

	@Override
	public String getRequestUrl(TileCoord tileCoord)
	{
		String url = this._url;
		if (!url.endsWith("?"))
		{
			url += "?";
		}
		HashMap<String, Object> paras = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : _paras.entrySet()) {
			paras.put(entry.getKey(),entry.getValue());
//			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
		paras.put("TRANSPARENT", true);
		paras.put("TileMatrix",tileCoord.Zoom + 1);
		paras.put("TileRow", tileCoord.Y);
		paras.put("TileCol", tileCoord.X);
		for (Map.Entry<String, Object> item : paras.entrySet())
		{
			url += (item.getKey() + "=" + item.getValue().toString() + "&");
		}
		return url;
	}

	@Override
	protected ITileGrid CreateTileGrid(double[] resolutions, Extent extent, Coordinate origin, Size tileSize)
	{
		return new WmtsTileGrid(resolutions, extent, origin, tileSize);
	}

}