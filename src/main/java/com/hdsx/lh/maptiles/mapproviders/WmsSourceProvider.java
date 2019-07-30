package com.hdsx.lh.maptiles.mapproviders;

import com.hdsx.lh.maptiles.config.MapConfig;
import com.hdsx.lh.maptiles.core.Extent;
import com.hdsx.lh.maptiles.core.TileCoord;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** 
 geoserver wms或其他标准的WMS服务
*/
public class WmsSourceProvider extends TmsSourceProvider
{

	public WmsSourceProvider(MapConfig config) {
		super(config);
	}


	@Override
	public String getRequestUrl(TileCoord tileCoord) {
		Extent tileExtent = _tileGrid.GetTileExtent(tileCoord);
		String url = this._url;
		if (!url.endsWith("?"))
		{
			url += "?";
		}
		HashMap<String, Object> paras = new HashMap<String, Object>();
		Set<Map.Entry<String, Object>> entrySet = _paras.entrySet();
		for (Map.Entry<String, Object> entry : entrySet) {
			paras.put(entry.getKey(),entry.getValue());
		}

		paras.put("TRANSPARENT", true);
		paras.put("WIDTH", this._tileGrid.getTileSize().Width);
		paras.put("HEIGHT", this._tileGrid.getTileSize().Height);
		paras.put("BBOX", tileExtent);
		for (Map.Entry<String, Object> item : paras.entrySet())
		{
			url += (item.getKey() + "=" + item.getValue().toString() + "&");
		}
		//Console.WriteLine(string.Format("x:{0},y:{1},zoom:{2},bbox:{3},url:{4}",tileCoord.X,tileCoord.Y,
		//    tileCoord.Zoom,tileExtent,url));
		return url;
	}
}