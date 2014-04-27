package com.peterfranza.stackserver.ui.client.manageusers;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.peterfranza.stackserver.ui.shared.AddUser;
import com.peterfranza.stackserver.ui.shared.DeleteUser;
import com.peterfranza.stackserver.ui.shared.FetchAllUsers;
import com.peterfranza.stackserver.ui.shared.UserCollectionResult;

public class ManageUsers extends Composite {

	private static ManageUsersUiBinder uiBinder = GWT
			.create(ManageUsersUiBinder.class);

	interface ManageUsersUiBinder extends UiBinder<Widget, ManageUsers> {
	}

	@UiField ListBox listBox;
	@UiField TextBox username;
	@UiField Button add;
	@UiField Button remove;
	
	private DispatchAsync dispatcher;
	
	Logger logger = Logger.getLogger("ManageUsers");
	
	private AsyncCallback<UserCollectionResult> loader = new AsyncCallback<UserCollectionResult>() {
		
		@Override
		public void onSuccess(UserCollectionResult result) {
			listBox.clear();
			for(String s: result.getList()) {
				listBox.addItem(s);
			}
			
			username.setValue("");
			listBox.setSelectedIndex(-1);
		}
		
		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
		}
	};
	
	@Inject
	public ManageUsers(DispatchAsync displatcher) {
		this.dispatcher = displatcher;
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		try {
			dispatcher.execute(new FetchAllUsers(), loader);			
		} catch(Exception e) {			
		    logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	@UiHandler("add")
	void onClickAdd(ClickEvent evt) {
		dispatcher.execute(new AddUser(username.getValue()), loader);
	}
	
	@UiHandler("remove")
	void onClickRemove(ClickEvent evt) {
		dispatcher.execute(new DeleteUser(listBox.getValue(listBox.getSelectedIndex())), loader);
	}
}
