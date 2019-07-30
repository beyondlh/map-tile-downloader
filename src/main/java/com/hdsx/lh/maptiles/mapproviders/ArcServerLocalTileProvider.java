package com.hdsx.lh.maptiles.mapproviders;

import com.hdsx.lh.maptiles.config.MapConfig;
import com.hdsx.lh.maptiles.core.TileCoord;

/**
 ArcServer本地缓存瓦片转换。ArcServer的本地缓存的瓦片的命名不太规则，且盗版的ArcGIS涉及版权风险。
 
 PS: 目的是为了规避风险，将瓦片全部重命名
*/
public class ArcServerLocalTileProvider extends WmtsSourceProvider
{
	private int offsetZoom;

	public ArcServerLocalTileProvider(MapConfig config)
	{
		super(config);
		this.offsetZoom = config.getOffsetZoom();
	}

	@Override
	public String getRequestUrl(TileCoord tileCoord)
	{
		String x = 'C' + padLeft((int)tileCoord.X, 8, 16);
		String y = 'R' + padLeft((int)tileCoord.Y, 8, 16); //WMTS
		//var y = 'R' + padLeft(Convert.ToInt32(-1 * tileCoord.Y -1), 8, 16);//TMS
		String z = 'L' + padLeft(tileCoord.Zoom + offsetZoom, 2, 10);

		String url = this._url;
		url = url.replace("{z}", z);
		url = url.replace("{y}", y);
		url = url.replace("{x}", x);
		return url;
	}

	//将10进制转16进制，余位补0，凑成ArcServer的切片格式
	private String padLeft(int val, int num)
	{
		return padLeft(val, num, 10);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: private string padLeft(int val, int num, int radix =10)
	private String padLeft(int val, int num, int radix) {
//		String str = String.valueOf(val, radix);
//		return tangible.DotNetToJavaStringHelper.padLeft(str, num, '0');
		return "";
	}

}