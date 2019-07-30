package com.hdsx.lh.maptiles.core;

import java.io.IOException;
import java.io.InputStream;

public interface ITileLoadStrategy
   {
       InputStream getTile(String url, int timeout) throws IOException;
   }