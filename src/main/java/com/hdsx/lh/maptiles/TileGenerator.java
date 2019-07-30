package com.hdsx.lh.maptiles;

import com.hdsx.lh.maptiles.config.MapConfig;
import com.hdsx.lh.maptiles.core.*;
import com.hdsx.lh.maptiles.mapproviders.ISourceProvider;
import com.hdsx.lh.maptiles.mapproviders.ProviderFactory;
import com.hdsx.lh.maptiles.utils.LoggerUtil;

import java.io.IOException;
import java.io.InputStream;

public class TileGenerator implements java.io.Closeable {
    private ITileOutputStrategy _outputStrategy = null;
    private ITileLoadStrategy _tileLoadStrategy = new HttpTileLoadStrategy();
    private ISourceProvider _source = null;
    private QueueTaskWorker<TileCoord> _worker = null;
    private MapConfig _mapConfig = null;
    private LimitedQueue<TileCoord> _lastTiles = null;


    public TileGenerator(MapConfig config) {
        _mapConfig = config;
        _source = ProviderFactory.CreateSourceProvider(config);
        _outputStrategy = ProviderFactory.CreateOutputStrategy(config);
        _outputStrategy.Init(config.getSavePath());
//        _worker = new QueueTaskWorker<TileCoordWrap>(config.runThreadCount, getTile; , true);
        _totalTile = _source.getTileGrid().getTotalTile();
        _lastTiles = new LimitedQueue<TileCoord>(config.getRunThreadCount());
        _successTileIndex = config.getMapResult().SuccessTiles;
        _currTileIndex = config.getMapResult().LastTileIndex;
    }

    private int _successTileIndex = 0;
    private int _failRetrySuccessIndex = 0;
    private int _failRetryIndex = 0;
    private int _currTileIndex = 0;

    public final int getSuccessTile() {
        return _successTileIndex;
    }

    private double _totalTile;

    public final double getTotalTile() {
        return _totalTile;
    }

    public final int getFailTiles() {
        return 404;
    }


    public final void Start() {
        TileCoord tileCoord = new TileCoord(_mapConfig.getOffsetZoom(),_mapConfig.getOrigin()[0],_mapConfig.getOrigin()[1],0);
        getTile(tileCoord);
    }


    protected void getTile(TileCoord tileCoord) {
        try {
            String url = _source.getRequestUrl(tileCoord);
            _currTileIndex++;
            /**
             * 记录最后一次Tile，下次执行时继续。
             * */
            _lastTiles.Enqueue(tileCoord);
            InputStream stream = _tileLoadStrategy.getTile(url, _mapConfig.getTimeout());
            _outputStrategy.Write(stream, _source.getOutputTile(tileCoord, _mapConfig.getOffsetZoom()));
        } catch (Exception ex) {
            LoggerUtil.error(ex.getMessage());
        } finally {
        }
    }

    @Override
    public void close() throws IOException {

    }
}