package com.hdsx.lh.maptiles.mapproviders;

public class BaiduTileProvider extends GenericTileProvider {
    private static final String UrlFormat = "http://online3.map.bdimg.com/onlinelabel/?qt=tile&x={0}&y={1}&z={2}&styles=pl&udt=20170712&scaler=1&p=1";

    @Override
    public int getMaxZoom() {
        return 18;
    }

    @Override
    public int getMinZoom() {
        return 1;
    }
}
