package com.hdsx.lh.maptiles.core;

import com.hdsx.lh.maptiles.utils.FileUtils;

import java.io.*;
import java.util.*;

/**
 * 默认的瓦片输出方式：以文件方式存储；
 */
public class DefaultOutputStrategy implements ITileOutputStrategy {
    protected String _rootPath;
    protected Map<String, String> _zoomFolderDic = new HashMap<String, String>();
    protected Map<String, String> _zoomAndXFolderDic = new HashMap<String, String>();

    public DefaultOutputStrategy() {

    }

    @Override
    public final void Init(String rootPath) {
        _rootPath = rootPath;
    }

    @Override
    public final void Write(InputStream input, OutputTile outputTile) {
        String filePath = BuildTilePath(outputTile);
        try  {
            byte[] content = FileUtils.getByteFromInputStream(input);
            FileUtils.writeFile(filePath,content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String BuildTilePath(OutputTile outputTile) {
        String zoomFolder;
        if (!_zoomFolderDic.containsKey(outputTile.Zoom)) {
            zoomFolder = java.nio.file.Paths.get(_rootPath).resolve(outputTile.Zoom).toString();
            (new java.io.File(zoomFolder)).mkdirs();
            _zoomFolderDic.put(outputTile.Zoom, zoomFolder);
        } else {
            zoomFolder = _zoomFolderDic.get(outputTile.Zoom);
        }

        String zoomXFolder;
        String zoomXKey = outputTile.Zoom + "_" + outputTile.X;
        if (!_zoomAndXFolderDic.containsKey(zoomXKey)) {
            zoomXFolder = java.nio.file.Paths.get(zoomFolder).resolve(outputTile.X).toString();
            (new java.io.File(zoomXFolder)).mkdirs();
            _zoomFolderDic.put(zoomXKey, zoomXFolder);
        } else {
            zoomXFolder = _zoomFolderDic.get(zoomXKey);
        }

        return java.nio.file.Paths.get(zoomXFolder).resolve(outputTile.Y + ".png").toString();
    }
}