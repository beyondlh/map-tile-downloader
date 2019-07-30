package com.hdsx.lh.maptiles.config;

import com.hdsx.lh.maptiles.core.MapResult;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "xx.test")
public class MapConfig {
    private int[] tileSize = new int[] { 256, 256 };
    private double[] extent;

    private double[] resolutions;
    
    private int offsetZoom;

    private int runThreadCount;

    private String savePath;

    private double[] origin;

    private String type;

    private String output;

    private String url;

    private Map<String,Object> urlParas;

    private MapResult mapResult = new MapResult();

    private int timeout;


    public int[] getTileSize() {
        return tileSize;
    }

    public void setTileSize(int[] tileSize) {
        this.tileSize = tileSize;
    }

    public double[] getExtent() {
        return extent;
    }

    public void setExtent(double[] extent) {
        this.extent = extent;
    }

    public double[] getResolutions() {
        return resolutions;
    }

    public void setResolutions(double[] resolutions) {
        this.resolutions = resolutions;
    }

    public int getOffsetZoom() {
        return offsetZoom;
    }

    public void setOffsetZoom(int offsetZoom) {
        this.offsetZoom = offsetZoom;
    }

    public int getRunThreadCount() {
        return runThreadCount;
    }

    public void setRunThreadCount(int runThreadCount) {
        this.runThreadCount = runThreadCount;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public double[] getOrigin() {
        return origin;
    }

    public void setOrigin(double[] origin) {
        this.origin = origin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getUrlParas() {
        return urlParas;
    }

    public void setUrlParas(Map<String, Object> urlParas) {
        this.urlParas = urlParas;
    }

    public MapResult getMapResult() {
        return mapResult;
    }

    public void setMapResult(MapResult mapResult) {
        this.mapResult = mapResult;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
