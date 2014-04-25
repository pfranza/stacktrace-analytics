package com.peterfranza.stackserver.ui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class StackServerEntryPoint implements EntryPoint {

	private final AppGinjector injector = GWT.create(AppGinjector.class);
	
	@Override
	public void onModuleLoad() {
		RootPanel.get().clear();
		RootPanel.get().add(injector.getMainContainer());
	}

}
