package com.peterfranza.stackserver.ui.client.manageapplications;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ManageApplications extends Composite {

	private static ManageApplicationsUiBinder uiBinder = GWT
			.create(ManageApplicationsUiBinder.class);

	interface ManageApplicationsUiBinder extends
			UiBinder<Widget, ManageApplications> {
	}

	public ManageApplications() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
