package com.peterfranza.stackserver.security.openid;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.peterfranza.stackserver.data.UserDataManager;

@Singleton
public class OpenIDLoginFilter implements Filter {

	private String endpoint = "https://www.google.com/accounts/o8/id";
	
	@Inject
	Provider<UserDataManager> userManager;
	
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		if(userManager.get().getUserCount() == 0) {
			arg2.doFilter(arg0, arg1);
			return;
		}
		
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession s = request.getSession(true);
		if(s.getAttribute("OpenIDAuthorizationID") == null) {
			s.setAttribute("OrigionalRequest", getFullURL(request));
			s.setAttribute("OpenIDEndpoint", endpoint);
			response.sendRedirect("/login");
		} else {
			arg2.doFilter(arg0, arg1);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	public static String getFullURL(HttpServletRequest request) {
	    StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();

	    if (queryString == null) {
	        return requestURL.toString();
	    } else {
	        return requestURL.append('?').append(queryString).toString();
	    }
	}
	
}
