package com.peterfranza.stackserver.ui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MainContainer extends Composite {

	private static MainContainerUiBinder uiBinder = GWT
			.create(MainContainerUiBinder.class);

	interface MainContainerUiBinder extends UiBinder<Widget, MainContainer> {
	}

	public MainContainer() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
