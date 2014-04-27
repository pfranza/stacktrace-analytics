package com.peterfranza.stackserver.ui.shared;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

public class UserCollectionResult implements Result {

	private ArrayList<String> list = new ArrayList<String>();
	
	public ArrayList<String> getList() {
		return list;
	}
	
	public void setList(ArrayList<String> list) {
		this.list = list;
	}
	
}
