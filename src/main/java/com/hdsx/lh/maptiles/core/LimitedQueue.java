package com.hdsx.lh.maptiles.core;

import java.util.*;

public class LimitedQueue<T> extends LinkedList<T> {
	private int limit = -1;

	public final int getLimit()
	{
		return limit;
	}
	public final void setLimit(int value)
	{
		limit = value;
	}

	public LimitedQueue(int limit) {
//		super(limit);
		this.setLimit(limit);
	}

	public final void Enqueue(T item) {
		if (this.size() >= this.getLimit())
		{
			this.removeFirst();
		}
		super.offer(item);
	}
}