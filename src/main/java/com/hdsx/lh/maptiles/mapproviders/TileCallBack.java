package com.hdsx.lh.maptiles.mapproviders;

import com.hdsx.lh.maptiles.core.TileCoord;

public interface TileCallBack {
    TileCoord call(TileCoord tileCoord);
//    Action<TileCoord> OnSuccess = null;
//    Action<TileCoord,Exception> OnFailed = null;
//    Action<TileCoord> OnFinally = null;
}
