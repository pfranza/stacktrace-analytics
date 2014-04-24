package com.peterfranza.stackserver.security.openid;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.ax.FetchRequest;

@Singleton
public class OpenIDRequestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -854837074811762591L;

	@Inject
	ConsumerManager manager;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		try {
            String returnToUrl = OpenIDLoginFilter.getFullURL(req).replace("/login", "/loginresponse");

            // perform discovery on the user-supplied identifier
            List<?> discoveries = manager.discover(req.getSession(true).getAttribute("OpenIDEndpoint").toString());

            // attempt to associate with the OpenID provider
            // and retrieve one service endpoint for authentication
            DiscoveryInformation discovered = manager.associate(discoveries);

            // store the discovery information in the user's session
            req.getSession().setAttribute("openid-disc", discovered);

            // obtain a AuthRequest message to be sent to the OpenID provider
            AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

            // Attribute Exchange example: fetching the 'email' attribute
            FetchRequest fetch = FetchRequest.createFetchRequest();
            fetch.addAttribute("email", "http://schema.openid.net/contact/email", true);                                      

            // attach the extension to the authentication request
            authReq.addExtension(fetch);


            resp.sendRedirect(authReq.getDestinationUrl(true));
        }
        catch (OpenIDException e) {
            throw new RuntimeException(e);
        }

		
	}
	
	
}
