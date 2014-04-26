package com.peterfranza.stackserver.ui.client.manageusers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ManageUsers extends Composite {

	private static ManageUsersUiBinder uiBinder = GWT
			.create(ManageUsersUiBinder.class);

	interface ManageUsersUiBinder extends UiBinder<Widget, ManageUsers> {
	}

	public ManageUsers() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
