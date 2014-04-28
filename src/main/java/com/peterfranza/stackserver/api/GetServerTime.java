package com.peterfranza.stackserver.api;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.peterfranza.stackserver.security.RequiresAuthentication;

@Path("/time")
public class GetServerTime {

	@GET
	@Produces("text/plain")
	@RequiresAuthentication
	public String getMsg() {
		return new Date().toString();
	}
	
}
