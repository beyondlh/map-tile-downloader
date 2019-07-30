package com.hdsx.lh.maptiles.core;

public class OutputTile {
	public OutputTile() {
	}

	public OutputTile(String zoom, String x, String y) {
		this.Zoom = zoom;
		this.X = x;
		this.Y = y;
	}

	public String Zoom;
	public String X;
	public String Y;
}