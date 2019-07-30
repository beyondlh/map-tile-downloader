package com.hdsx.lh.maptiles.mapproviders;


import com.hdsx.lh.maptiles.config.MapConfig;
import com.hdsx.lh.maptiles.core.TileCoord;

/**
 高德地图
 
 高德地图实际是WMTS规则，但是实际计算时，发现总是少一列，用TMS换算后计算就没有问题。因此用TMS规则下载。
 请注意：因此离线瓦片存储是按TMS规则存储
*/
public class GaodeMapProvider extends TmsSourceProvider {

	public GaodeMapProvider(MapConfig config) {
		super(config);
	}

	@Override
	public String getRequestUrl(TileCoord tileCoord){
		//http://webrd01.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x=" + x + "&y=" + y + "&z=" + z
		String url = this._url;
		url = url.replace("{z}",  String.valueOf(_offsetZoom + tileCoord.Zoom));
		url = url.replace("{x}", String.valueOf(tileCoord.X));
		url = url.replace("{y}", String.valueOf(-1 * tileCoord.Y - 1));
		return url;
	}
}