package com.peterfranza.stackserver.ui.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(ApplicationBindings.class)
public interface AppGinjector extends Ginjector {

//	EventBus getEventBus();
//	PlaceController getPlaceController();
//	AppPlaceHistoryMapper getAppPlaceHistoryMapper();
	MainContainer getMainContainer();
	
}
