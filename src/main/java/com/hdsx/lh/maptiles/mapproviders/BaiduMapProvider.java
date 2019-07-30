package com.hdsx.lh.maptiles.mapproviders;


import com.hdsx.lh.maptiles.config.MapConfig;
import com.hdsx.lh.maptiles.core.OutputTile;
import com.hdsx.lh.maptiles.core.TileCoord;

/**
 百度地图
 
 符合TMS瓦片规则，特殊之处只是Origin在[0，0]，标准的TMS的Origin在extent的左下角
*/
public class BaiduMapProvider extends TmsSourceProvider
{
	private int offsetZoom;
	public BaiduMapProvider(MapConfig config) {
		super(config);
		this.offsetZoom = config.getOffsetZoom();
	}

	@Override
	public String getRequestUrl(TileCoord tileCoord) {
		String url = this._url;
		String x = String.valueOf(tileCoord.X);
		String y = String.valueOf(tileCoord.Y);
		if (tileCoord.X < 0)
		{
			x = "M" + Math.abs(tileCoord.X);
		}
		if (tileCoord.Y < 0)
		{
			y = "M" + Math.abs(tileCoord.Y);
		}
		url = url.replace("{z}", String.valueOf(offsetZoom + tileCoord.Zoom));
		url = url.replace("{x}", String.valueOf(tileCoord.X));
		url = url.replace("{y}", String.valueOf(tileCoord.Y));
		//url += "&x=" + tileCoord.X.ToString();
		//url += "&y=" + tileCoord.Y.ToString();
		//url += "&z=" + (offsetZoom + tileCoord.Zoom).ToString();
		//url = string.Format(url, tileCoord.Zoom, Math.Abs(tileCoord.Y), Math.Abs(tileCoord.X));
		return url;
	}

	@Override
	public OutputTile getOutputTile(TileCoord input, int zoomOffset) {
		String x = String.valueOf(input.X), y = String.valueOf(input.Y);
		if (input.X < 0){
			x = "M" + Math.abs(input.X);
		}
		if (input.Y < 0){
			y = "M" + Math.abs(input.Y);
		}
		return new OutputTile(String.valueOf((input.Zoom + zoomOffset)), x, y);
	}
}