package com.peterfranza.stackserver.client;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Singleton
public class StackServerClientFilter implements Filter {
	
	@Inject
	StackServerClient client;
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		try {
			arg2.doFilter(arg0, arg1);
		} catch(IOException e) {
			client.submit(e);
			throw e;
		} catch(ServletException e) {
			client.submit(e);
			throw e;
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
