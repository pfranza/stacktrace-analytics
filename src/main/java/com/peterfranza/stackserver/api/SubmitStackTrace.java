package com.peterfranza.stackserver.api;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.gson.Gson;
import com.peterfranza.stackserver.data.ApplicationDataManager;
import com.peterfranza.stackserver.security.RequiresAuthentication;
import com.peterfranza.stackserver.ui.shared.model.ApplicationModel;

@Path("/submit")
public class SubmitStackTrace {

	@Inject	Provider<ApplicationModel> application;
	@Inject	Provider<ApplicationDataManager> dataManager;
	
	@POST
	@RequiresAuthentication
	public String submitNewStackTrace(@FormParam("data") String data) {
		TraceDefinition d = new Gson().fromJson(data, TraceDefinition.class);
		dataManager.get().appendTrace(application.get(), d.hostSignature,
				d.data, d.version, d.traceElements);
		return "OK";
	}
	
	@SuppressWarnings("unused")
	private static class TraceDefinition implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3921132426640666184L;
		
		public String hostSignature;
		public String data;
		public String version;
		public StackTraceElement[] traceElements;
	}
	
}
