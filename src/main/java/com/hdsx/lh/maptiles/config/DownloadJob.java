package com.hdsx.lh.maptiles.config;

import com.hdsx.lh.maptiles.mapproviders.TileProvider;
import com.hdsx.lh.maptiles.utils.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DownloadJob {
    private static final Logger log = Logger.getLogger(DownloadJob.class.getName());

    private Properties prop = new Properties();

    private String outputZoomLevels = "";
    private String tileServer = "";
    private String outputLocation = "";
    private String type = "";

    private static final String OUTPUT_ZOOM_LEVEL = "OutputZoomLevel";
    private static final String TILE_SERVER = "TileServer";
    private static final String OUTPUTLOCATION = "OutputLocation";

    public static final String TYPE = "Type";

    public DownloadJob() {

    }

    /**
     * constructor setting propertyFileName
     *
     * @param propertyFileName
     */
    public DownloadJob(String propertyFileName) {
        loadFromFile(propertyFileName);
    }

    public void saveToFile(String propertyFileName) {
        setTemplateProperty(prop, OUTPUT_ZOOM_LEVEL, outputZoomLevels);
        setTemplateProperty(prop, TILE_SERVER, tileServer);
        setTemplateProperty(prop, OUTPUTLOCATION, outputLocation);
        try {
            prop.storeToXML(new FileOutputStream(propertyFileName), null);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error saving job to file " + propertyFileName, e);
        }
    }

    private void loadFromFile(String fileName) {
        try {
            prop.loadFromXML(new FileInputStream(fileName));
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error loading job from file " + fileName, e);
        }

        type = prop.getProperty(TYPE, "");
        outputZoomLevels = prop.getProperty(OUTPUT_ZOOM_LEVEL, "12");
        tileServer = prop.getProperty(TILE_SERVER, "");
        outputLocation = prop.getProperty(OUTPUTLOCATION, "tiles");
    }

    protected void setTemplateProperty(Properties prop, String key, String value) {
        log.log(Level.CONFIG, "setting property {0} to value {1}", new Object[]{key, value});
        prop.setProperty(key, value);
    }

    /**
     * Getter for outputZoomLevel
     *
     * @return the outputZoomLevel
     */
    public final String getOutputZoomLevels() {
        return outputZoomLevels;
    }

    /**
     * Setter for outputZoomLevel
     *
     * @param outputZoomLevel the outputZoomLevel to set
     */
    public final void setOutputZoomLevels(String outputZoomLevel) {
        outputZoomLevels = outputZoomLevel;
    }

    /**
     * Getter for tileServer
     *
     * @return the tileServer
     */
    public final String getTileServer() {
        return tileServer;
    }

    public final TileProvider getTileProvider() {
        return Util.getTileProvider(getTileServer());
    }

    /**
     * Setter for tileServer
     *
     * @param tileServer the tileServer to set
     */
    public final void setTileServer(String tileServer) {
        this.tileServer = tileServer;
    }

    /**
     * Getter for outputLocation
     *
     * @return the outputLocation
     */
    public final String getOutputLocation() {
        return outputLocation;
    }

    /**
     * Setter for outputLocation
     *
     * @param outputLocation the outputLocation to set
     */
    public final void setOutputLocation(String outputLocation) {
        this.outputLocation = outputLocation;
    }

    /**
     * Getter for type
     *
     * @return the type
     */
    public final String getType() {
        return type;
    }
}
