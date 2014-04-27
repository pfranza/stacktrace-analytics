package com.peterfranza.stackserver.ui.client.settings;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.peterfranza.stackserver.ui.client.manageapplications.ManageApplications;
import com.peterfranza.stackserver.ui.client.manageusers.ManageUsers;

public class SettingsPanel extends Composite {

	private static SettingsPanelUiBinder uiBinder = GWT
			.create(SettingsPanelUiBinder.class);

	interface SettingsPanelUiBinder extends UiBinder<Widget, SettingsPanel> {
	}

	private Provider<ManageUsers> manageUsers;
	private Provider<ManageApplications> manageApplications;
	
	@UiField TabPanel mainTab;
	@UiField public Button close;
	
	@Inject
	public SettingsPanel(Provider<ManageUsers> manageUsers, Provider<ManageApplications> manageApplications) {
		this.manageUsers = manageUsers;
		this.manageApplications = manageApplications;
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiFactory
	public ManageUsers getUsers() {
		return manageUsers.get();
	}
	
	@UiFactory
	public ManageApplications getApplications() {
		return manageApplications.get();
	}

}
