package com.hdsx.lh.maptiles.core;

import java.io.InputStream;
import java.util.stream.Stream;

/**
 瓦片输出的策略
*/
public interface ITileOutputStrategy
{

	void Init(String rootPath);

	void Write(InputStream input, OutputTile outputTile);
}