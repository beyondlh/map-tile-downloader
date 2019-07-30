package com.hdsx.lh.maptiles.mapproviders;


import com.hdsx.lh.maptiles.config.MapConfig;


/**
 ArcServer Rest瓦片请求
*/
public class ArcServerRestProvider extends WmtsXyzTileSourceProvider
{
	public ArcServerRestProvider(MapConfig config)
	{
		super(config);
	}
}