package com.hdsx.lh.maptiles.storage;

import com.hdsx.lh.maptiles.models.Tile;

import java.util.Date;


public interface TileStore {
    /// <summary>
    /// 存入数据库
    /// </summary>
    /// <param name="type">瓦片类型</param>
    /// <param name="pos">坐标</param>
    /// <param name="tile">瓦片的内容</param>
    /// <returns></returns>
    boolean PutTileToStore(int type, Tile pos, byte[] tile);

    /// <summary>
    /// 从数据库获取瓦片
    /// </summary>
    /// <param name="x"></param>
    /// <param name="y"></param>
    /// <param name="zoom"></param>
    /// <param name="type">瓦片类型</param>
    /// <returns></returns>
    void GetTileFromStore(long x, long y, int zoom, int type);

    /// <summary>
    /// 删除某一个日期之前的所有瓦片
    /// </summary>
    /// <param name="date">日期.</param>
    /// <param name="type">地图类型</param>
    /// <returns>被删除的瓦片数量</returns>
    int DeleteOlderThan(Date  date);
}
