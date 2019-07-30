package com.hdsx.lh.maptiles.core;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;
import java.util.function.Function;

public class QueueTaskWorker<T> {
    private BlockingQueue<T> _queue;
    private boolean _isClosed = false;
    private int _threadCount = 1;
    private boolean _isBlocking = false;
    private Thread[] _tasks = null;
    Callable<TileCoord> _getTile = null;

    public QueueTaskWorker(int threadCount,Callable<TileCoord> getTile, boolean isBlock) {
        _threadCount = threadCount;
        _isBlocking = isBlock;
        _getTile = getTile;
        Init();
    }

    public final void TryQueue(T item) {
        _queue.add(item);
    }

    public final void Init() {
        if (_isBlocking) {
            _queue = new LinkedBlockingQueue<T>(_threadCount);
        } else {
            _queue = new LinkedBlockingQueue<T>();
        }
    }

    public final void Start() {
        //开启多线程执行抓取任务
        try {
            _getTile.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void Close() {
        _isClosed = true;
    }

    public final void Dispose() {
        Close();
    }
}