package com.hdsx.lh.maptiles.mapproviders;

import com.hdsx.lh.maptiles.config.MapConfig;
import com.hdsx.lh.maptiles.core.*;

public class ProviderFactory {
    public static ISourceProvider CreateSourceProvider(MapConfig config) {
        ISourceProvider source = null;
        switch (config.getType().toLowerCase()) {
            case "baidu":
                source = new BaiduMapProvider(config);
                break;
            case "gaode":
                source = new GaodeMapProvider(config);
                break;
            case "tencent":
                source = new TencentMapProvider(config);
                break;
            case "tms":
                source = new TmsSourceProvider(config);
                break;
            case "wmts":
                source = new WmtsSourceProvider(config);
                break;
            case "wmtsxyz":
                source = new WmtsXyzTileSourceProvider(config);
                break;
            case "wms":
                source = new WmsSourceProvider(config);
                break;
            case "arcserverrest":
                source = new ArcServerRestProvider(config);
                break;
            case "arcserverlocaltile":
                source = new ArcServerLocalTileProvider(config);
                break;
            default:
                source = new GaodeMapProvider(config);
        }
        return source;
    }

    public static ITileOutputStrategy CreateOutputStrategy(MapConfig config) {
        ITileOutputStrategy result = null;
        switch (config.getOutput().toLowerCase()) {
            case "file":
                result = new DefaultOutputStrategy();
                break;
//            case "sqlite":
//                result = new SqliteOutputStrategy();
//                break;
//            case "sqliteandbase64":
//                result = new SqliteAndBase64OutputStrategy();
//                break;
            default:
                result = new DefaultOutputStrategy();
                break;
        }
        return result;
    }
}