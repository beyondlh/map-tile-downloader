package com.hdsx.lh.maptiles.models;

public class StorageQueueItem {
    /// <summary>
    ///    X坐标
    /// </summary>
    public long X;

    /// <summary>
    ///    Y坐标
    /// </summary>
    public long Y;

    /// <summary>
    ///    Zoom
    /// </summary>
    public int Zoom;

    /// <summary>
    ///    构造函数
    /// </summary>
    /// <param name="x">X</param>
    /// <param name="y">Y</param>
    /// <param name="zoom">zoom</param>

    public StorageQueueItem(long x, long y, int zoom) {
        X = x;
        Y = y;
        Zoom = zoom;
    }


    @Override
    public String toString() {
        return "待存储瓦片StorageQueueItem{" +
                "X=" + X +
                ", Y=" + Y +
                ", Zoom=" + Zoom +
                '}';
    }
}
