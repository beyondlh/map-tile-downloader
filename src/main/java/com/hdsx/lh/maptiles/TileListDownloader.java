package com.hdsx.lh.maptiles;

import com.hdsx.lh.maptiles.datatypes.TileDownloadError;
import com.hdsx.lh.maptiles.datatypes.TileDownloadResult;
import com.hdsx.lh.maptiles.mapproviders.TileProvider;
import com.hdsx.lh.maptiles.models.Tile;
import com.hdsx.lh.maptiles.storage.TileList;
import com.hdsx.lh.maptiles.utils.LoggerUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

public class TileListDownloader {
    private LinkedList<Tile> tilesToDownload;
    private String downloadPath;
    private TileProvider tileProvider;
    private TileList tileList;
    private ArrayList<TileListDownloaderThread> downloaderThreads = new ArrayList<TileListDownloaderThread>();

    private int numberOfTilesToDownload = 0;
    private int errorCount = 0;
    private int updatedTilesCount = 0;

    private LinkedList<TileDownloadError> errorTileList = new LinkedList<>();

    private static final int MAX_RETRIES = 5;

    public TileListDownloader(String downloadPath, TileList tilesToDownload, TileProvider tileProvider) {
        super();
        setDownloadPath(downloadPath);
        setTilesToDownload(tilesToDownload.getTileListToDownload());
        this.tileProvider = tileProvider;
    }

    public void setTilesToDownload(ArrayList<Tile> pTilesToDownload) {
        if (this.tilesToDownload != null) {
            this.tilesToDownload = new LinkedList<>(pTilesToDownload);
        } else {
            this.tilesToDownload = new LinkedList<>();
        }
        numberOfTilesToDownload = pTilesToDownload.size();
    }


    /**
     * Get tile to download
     *
     * @return a tile
     */
    synchronized private Tile getTilesToDownload() {
        return this.tilesToDownload.poll();
    }

    synchronized private void requeueTile(Tile tile) {
        this.tilesToDownload.add(tile);
    }


    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public TileProvider getTileProvider() {
        return tileProvider;
    }

    public void setTileProvider(TileProvider tileProvider) {
        this.tileProvider = tileProvider;
    }

    public int getNumberOfTilesToDownload() {
        return numberOfTilesToDownload;
    }

    public void setNumberOfTilesToDownload(int numberOfTilesToDownload) {
        this.numberOfTilesToDownload = numberOfTilesToDownload;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getUpdatedTilesCount() {
        return updatedTilesCount;
    }

    public void setUpdatedTilesCount(int updatedTilesCount) {
        this.updatedTilesCount = updatedTilesCount;
    }

    public LinkedList<TileDownloadError> getErrorTileList() {
        return errorTileList;
    }

    public void setErrorTileList(LinkedList<TileDownloadError> errorTileList) {
        this.errorTileList = errorTileList;
    }

    public static int getMaxRetries() {
        return MAX_RETRIES;
    }

    synchronized private void increaseUpdatedCount() {
        updatedTilesCount++;
    }

    synchronized private void addedTileDownloadError(TileDownloadResult result, Tile tileToDownload) {
        errorCount++;
        TileDownloadError error = new TileDownloadError();
        error.setTile(tileToDownload);
        error.setResult(result);
        errorTileList.add(error);
    }

    private TileDownloadResult doDownload(Tile tileToDownload) {
        TileDownloadResult result = new TileDownloadResult();
        URL url = null;
        try {
            url = new URL(this.tileProvider.getTileUrl(tileToDownload));
        } catch (MalformedURLException e) {
            result.setCode(TileDownloadResult.CODE_MALFORMED_URL_EXECPTION);
            result.setMessage(TileDownloadResult.MSG_MALFORMED_URL_EXECPTION);
            return result;
        }
        String fileName = getDownloadPath() + File.separator + tileProvider.getTileFilename(tileToDownload);
        String filePath = getDownloadPath() + File.separator + tileToDownload.getPath();

        File testDir = new File(filePath);
        if (!testDir.exists()) {
            testDir.mkdirs();
        }
        for (int retries = 0; retries < MAX_RETRIES; retries++) {
            result = doSingleDownload(fileName, url);
            if (result.getCode() == TileDownloadResult.CODE_OK) {
//                fireDownloadedTileEvent(fileName, result.isUpdatedTile());
                break;
            } else if (result.getCode() == TileDownloadResult.CODE_HTTP_500) {
                // HTTP-500 Error - retry again
//                fireWaitHttp500ErrorToResume("HTTP/500 - wait 10 sec. to retry");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    retries = MAX_RETRIES;
                    Thread.currentThread().interrupt();
                }
            } else {
//                unknown error
                return result;
            }

        }

        return result;
    }

    /**
     * @param fileName
     * @param url
     * @return TileDownloadResult
     */
    private TileDownloadResult doSingleDownload(String fileName, URL url) {
        TileDownloadResult result = new TileDownloadResult();

        File file = new File(fileName);
        if (file.exists()) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, -24 * 7);
            if (file.lastModified() >= cal.getTimeInMillis()) {
                result.setCode(TileDownloadResult.CODE_OK);
                result.setMessage(TileDownloadResult.MSG_OK);
                return result;
            }
        }

        HttpURLConnection urlConnection = null;
        boolean imageNeedsToBeErased = false;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestProperty("User-Agent", "JTileDownloader/" + Constants.VERSION);
            urlConnection.setUseCaches(false);

            // iflastmodifiedsince would work like this and you would get a 304 response code
            // but it seems as if no tile server supports this so far
            urlConnection.setIfModifiedSince(file.lastModified());

            long lastModified = urlConnection.getLastModified();

            // do not overwrite file if not changed: required because setIfModifiedSince doesn't work for tile-servers atm
            // Mapnik-Servers do not send LastModified-headers...
            if (file.length() > 0) {
                if ((lastModified != 0 && file.lastModified() >= lastModified) || urlConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_MODIFIED) {
                    result.setCode(TileDownloadResult.CODE_OK);
                    result.setMessage(TileDownloadResult.MSG_OK);
                    return result;
                }
            }

            InputStream inputStream = urlConnection.getInputStream();

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            imageNeedsToBeErased = true;
            int temp = inputStream.read();
            while (temp != -1) {
                bufferedOutputStream.write(temp);
                temp = inputStream.read();
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            imageNeedsToBeErased = false;
        } catch (FileNotFoundException e) {
            LoggerUtil.error("错误", "Error downloading tile", e);
            result.setCode(TileDownloadResult.CODE_FILENOTFOUND);
            result.setMessage(TileDownloadResult.MSG_FILENOTFOUND);
            return result;
        } catch (UnknownHostException e) {
            LoggerUtil.error("Could not find host for a tile", e);
            result.setCode(TileDownloadResult.CODE_UNKNOWN_HOST_EXECPTION);
            result.setMessage(TileDownloadResult.MSG_UNKNOWN_HOST_EXECPTION);
            return result;
        } catch (IOException e) {
            LoggerUtil.error("Error downloading tile", e);
            try {
                if (urlConnection != null && urlConnection.getResponseCode() == 500) {
                    result.setCode(TileDownloadResult.CODE_HTTP_500);
                    result.setMessage(TileDownloadResult.MSG_HTTP_500);
                    return result;
                } else {
                    result.setCode(TileDownloadResult.CODE_UNKNOWN_ERROR);
                    if (urlConnection != null && urlConnection.getResponseMessage().length() > 0) {
                        result.setMessage(urlConnection.getResponseMessage());
                    } else {
                        result.setMessage(TileDownloadResult.MSG_UNKNOWN_ERROR);
                    }
                    return result;
                }
            } catch (IOException e1) {
//                LoggerUtil.log(Level.SEVERE, "Error getting response message", e1);
                result.setCode(TileDownloadResult.CODE_UNKNOWN_ERROR);
                result.setMessage(TileDownloadResult.MSG_UNKNOWN_ERROR);
                return result;
            } catch (Throwable th) {
//                LoggerUtil.log(Level.SEVERE, "Error getting response message", th);
                result.setCode(TileDownloadResult.CODE_UNKNOWN_ERROR);
                result.setMessage(TileDownloadResult.MSG_UNKNOWN_ERROR);
                return result;
            }
        } finally {
            if (imageNeedsToBeErased) {
//                LoggerUtil.info("Deleting incomplete tile " + file.getPath());
                try {
                    file.delete();
                } catch (Exception e) {
//                    LoggerUtil.log(Level.SEVERE, "Could not delete", e);
                }
            }
        }

        result.setCode(TileDownloadResult.CODE_OK);
        result.setMessage(TileDownloadResult.MSG_OK);
        result.setUpdatedTile(true);
        return result;
    }


    public class TileListDownloaderThread extends Thread {

        @Override
        public void run() {
            Tile tileToDownload = null;
            int tilesDownloaded = 0;
            while ((tileToDownload = getTilesToDownload()) != null) {
                if (tilesDownloaded > 0) {
                    TileDownloadResult result = doDownload(tileToDownload);
                    if (result.getCode() != TileDownloadResult.CODE_OK) {
                        addedTileDownloadError(result, tileToDownload);
                    }
                }
                tilesDownloaded++;
            }

        }
    }


}
