package com.peterfranza.stackserver.ui.shared;

import java.io.Serializable;

public abstract class AbstractPagingCollectionResult<T extends Serializable> extends AbstractCollectionResult<T> {

	private int count;
	private int start;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
	
	
}
