package com.peterfranza.stackserver.ui.client;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.peterfranza.stackserver.ui.client.settings.SettingsPanel;
import com.peterfranza.stackserver.ui.client.viewtraces.ViewTraces;

public class MainContainer extends Composite {

	private static MainContainerUiBinder uiBinder = GWT
			.create(MainContainerUiBinder.class);

	interface MainContainerUiBinder extends UiBinder<Widget, MainContainer> {
	}
	
	@UiField TabPanel mainTab;
	
	@Inject Provider<SettingsPanel> settingsProvider;

	private Provider<ViewTraces> viewTracesProvider;
	
	@Inject 
	public MainContainer(Provider<ViewTraces> viewTracesProvider) {
		this.viewTracesProvider = viewTracesProvider;
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	@UiHandler("settings")
	void handleAddNew(ClickEvent e) {
		final DialogBox dialog = new DialogBox(false, true);
		dialog.setText("Configure Settings");
		SettingsPanel s = settingsProvider.get();
		dialog.add(s);
		s.close.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				dialog.hide();
			}
		});
		dialog.center();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				dialog.center();
			}
		});
	}
	
	@UiFactory
	public ViewTraces getViewTraces() {
		return viewTracesProvider.get();
	}

}
