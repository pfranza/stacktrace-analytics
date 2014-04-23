package com.peterfranza.stackserver.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.peterfranza.stackserver.security.RequiresAuthentication;

@Path("/submit")
public class SubmitStackTrace {

	@POST
	@RequiresAuthentication
	public String submitNewStackTrace() {
		return "OK";
	}
	
}
