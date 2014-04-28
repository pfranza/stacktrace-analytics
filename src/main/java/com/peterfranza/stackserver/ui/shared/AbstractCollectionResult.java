package com.peterfranza.stackserver.ui.shared;

import java.io.Serializable;
import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

public abstract class AbstractCollectionResult<T extends Serializable> implements Result {
	
	private ArrayList<T> list = new ArrayList<T>();
	
	public ArrayList<T> getList() {
		return list;
	}
	
	public void setList(ArrayList<T> list) {
		this.list = list;
	}
}
