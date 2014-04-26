package com.peterfranza.stackserver.ui.client.settings;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class SettingsPanel extends Composite {

	private static SettingsPanelUiBinder uiBinder = GWT
			.create(SettingsPanelUiBinder.class);

	interface SettingsPanelUiBinder extends UiBinder<Widget, SettingsPanel> {
	}

	@UiField TabPanel mainTab;
	@UiField public Button close;
	
	public SettingsPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
