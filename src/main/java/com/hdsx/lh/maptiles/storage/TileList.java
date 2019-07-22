package com.hdsx.lh.maptiles.storage;

import com.hdsx.lh.maptiles.models.Tile;

import java.util.ArrayList;

public interface TileList {

    /**
     * @return {@link ArrayList} containing Tiles
     */
    public abstract ArrayList<Tile> getTileListToDownload();
}
