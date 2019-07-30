package com.hdsx.lh.maptiles;

import com.hdsx.lh.maptiles.config.MapConfig;

public class DownLoadTiles {
    public static void main(String[] args) {
        MapConfig mapConfig = new MapConfig();
        mapConfig.setResolutions(new double[]{256, 128, 64, 32, 16});
        mapConfig.setOffsetZoom(10);
        mapConfig.setTileSize(new int[]{256, 256});
        mapConfig.setExtent(new double[]{11808770.385317, 3403500.2612752, 11892674.468269, 3442476.25119});
        mapConfig.setOrigin(new double[]{0, 0});
        mapConfig.setType("baidu");
        mapConfig.setOutput("file");
        mapConfig.setUrl("http://online3.map.bdimg.com/onlinelabel/?qt=tile&styles=pl&udt=20151021&scaler=1&p=1&qt=tile&x={x}&y={y}&z={z}");
        TileGenerator tileGenerator = new TileGenerator(mapConfig);
        tileGenerator.Start();
    }
}
