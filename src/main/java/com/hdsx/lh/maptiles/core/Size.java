package com.hdsx.lh.maptiles.core;

public class Size {
	public Size(int[] size) {
		if (size == null || size.length < 2) {
			throw new IllegalArgumentException("extent must be array [width,height]");
		}
		Width = size[0];
		Height = size[1];
	}
	public int Width;
	public int Height;
}