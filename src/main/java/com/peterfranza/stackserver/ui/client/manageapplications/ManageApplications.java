package com.peterfranza.stackserver.ui.client.manageapplications;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ManageApplications extends Composite {

	private static ManageApplicationsUiBinder uiBinder = GWT
			.create(ManageApplicationsUiBinder.class);

	interface ManageApplicationsUiBinder extends
			UiBinder<Widget, ManageApplications> {
	}

	@UiField ListBox listBox;
	@UiField TextBox applicationName;
	@UiField TextBox applicationDescription;
	@UiField Button add;
	@UiField Button remove;
	
	@UiField Label apiKey;
	@UiField Label token;
	@UiField Label consumer;
	
	public ManageApplications() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}

