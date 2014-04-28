package com.peterfranza.stackserver.ui.client.manageapplications;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.peterfranza.stackserver.ui.shared.AddApplication;
import com.peterfranza.stackserver.ui.shared.AddUser;
import com.peterfranza.stackserver.ui.shared.ApplicationCollectionResult;
import com.peterfranza.stackserver.ui.shared.FetchAllApplications;
import com.peterfranza.stackserver.ui.shared.FetchAllUsers;
import com.peterfranza.stackserver.ui.shared.UserCollectionResult;
import com.peterfranza.stackserver.ui.shared.model.ApplicationModel;

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
	
	@Inject
	DispatchAsync dispatcher;
	
	Logger logger = Logger.getLogger("ManageUsers");
	
	private ArrayList<ApplicationModel> cache = new ArrayList<ApplicationModel>();
	
	private AsyncCallback<ApplicationCollectionResult> loader = new AsyncCallback<ApplicationCollectionResult>() {
		
		@Override
		public void onSuccess(ApplicationCollectionResult result) {
			cache.clear();
			listBox.clear();
			for(ApplicationModel s: result.getList()) {
				listBox.addItem(s.getApplicationName(), s.getApiKey());
			}
			
			applicationName.setValue("");
			applicationDescription.setValue("");
			listBox.setSelectedIndex(-1);
			cache.addAll(result.getList());
		}
		
		@Override
		public void onFailure(Throwable caught) {
			caught.printStackTrace();
		}
	};
	
	public ManageApplications() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		try {
			dispatcher.execute(new FetchAllApplications(), loader);			
		} catch(Exception e) {			
		    logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	@UiHandler("add")
	void onClickAdd(ClickEvent evt) {
		dispatcher.execute(new AddApplication(applicationName.getValue(), applicationDescription.getValue()), loader);
	}
	
	@UiHandler("listBox")
	void onChangeAdd(ChangeEvent evt) {
		if(listBox.getSelectedIndex() == -1) {
			apiKey.setText("");
			token.setText("");
			consumer.setText("");
		} else {
			ApplicationModel m = cache.get(listBox.getSelectedIndex());
			apiKey.setText(m.getApiKey());
			token.setText(m.getTokenSecret());
			consumer.setText(m.getConsumerSecret());
		}
	}

}


