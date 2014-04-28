package com.peterfranza.stackserver.ui.shared;

import net.customware.gwt.dispatch.shared.Action;

public class FetchStackTraceList implements Action<StackTraceEntryCollectionResult>{

	private int start;
	private int length;

	FetchStackTraceList(){}
	public FetchStackTraceList(int start, int length) {
		this.start = start;
		this.length = length;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getLength() {
		return length;
	}

}
