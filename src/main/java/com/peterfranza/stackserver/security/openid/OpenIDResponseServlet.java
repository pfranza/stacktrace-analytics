package com.peterfranza.stackserver.security.openid;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegResponse;

import com.peterfranza.stackserver.data.UserDataManager;

@Singleton
public class OpenIDResponseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5197651099252125633L;

	@Inject
	ConsumerManager manager;
	
	@Inject
	Provider<UserDataManager> userManager;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Identifier identifier = this.verifyResponse(request);
		if (identifier == null) {
			resp.sendRedirect("/login");
		} else {
			String email = ((Map<String,String>)request.getAttribute("attributes")).get("email");
			if(userManager.get().isUserAuthorized(email)) {
				HttpSession s = request.getSession(true);			
				s.setAttribute("OpenIDAuthorizationID", identifier.getIdentifier());
				userManager.get().setUserAuthorizatonId(email, identifier.getIdentifier());
				resp.sendRedirect(s.getAttribute("OrigionalRequest").toString());
			} else {
				resp.sendError(403);
			}
		}
		
		
	}
	
	 // --- processing the authentication response ---
    public Identifier verifyResponse(HttpServletRequest httpReq)
                    throws ServletException {
            try {
                    // extract the parameters from the authentication response
                    // (which comes in as a HTTP request from the OpenID provider)
                    ParameterList response = new ParameterList(httpReq
                                    .getParameterMap());

                    // retrieve the previously stored discovery information
                    DiscoveryInformation discovered = (DiscoveryInformation) httpReq
                                    .getSession().getAttribute("openid-disc");

                    // extract the receiving URL from the HTTP request
                    StringBuffer receivingURL = httpReq.getRequestURL();
                    String queryString = httpReq.getQueryString();
                    if (queryString != null && queryString.length() > 0)
                            receivingURL.append("?").append(httpReq.getQueryString());

                    // verify the response; ConsumerManager needs to be the same
                    // (static) instance used to place the authentication request
                    VerificationResult verification = manager.verify(receivingURL
                                    .toString(), response, discovered);

                    // examine the verification result and extract the verified
                    // identifier
                    Identifier verified = verification.getVerifiedId();
                    if (verified != null) {
                            AuthSuccess authSuccess = (AuthSuccess) verification
                                            .getAuthResponse();

                            receiveSimpleRegistration(httpReq, authSuccess);

                            receiveAttributeExchange(httpReq, authSuccess);

                            return verified; // success
                    }
            } catch (OpenIDException e) {
                    throw new ServletException(e);
            }

            return null;
    }
    
    private void receiveSimpleRegistration(HttpServletRequest httpReq,
    		AuthSuccess authSuccess) throws MessageException {
    	if (authSuccess.hasExtension(SRegMessage.OPENID_NS_SREG)) {
    		MessageExtension ext = authSuccess
    				.getExtension(SRegMessage.OPENID_NS_SREG);
    		if (ext instanceof SRegResponse) {
    			SRegResponse sregResp = (SRegResponse) ext;
    			for (Iterator<?> iter = sregResp.getAttributeNames().iterator(); iter.hasNext();) {
    				String name = (String) iter.next();
    				String value = sregResp.getParameterValue(name);
    				httpReq.setAttribute(name, value);
    			}
    		}
    	}
    }

    /**
     * @param httpReq
     * @param authSuccess
     * @throws MessageException 
     */
    private void receiveAttributeExchange(HttpServletRequest httpReq,
    		AuthSuccess authSuccess) throws MessageException {
    	if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
    		FetchResponse fetchResp = (FetchResponse) authSuccess
    				.getExtension(AxMessage.OPENID_NS_AX);

    		// List emails = fetchResp.getAttributeValues("email");
    		// String email = (String) emails.get(0);

    		List<?> aliases = fetchResp.getAttributeAliases();
    		Map<String,String> attributes = new LinkedHashMap<String,String>();
    		for (Iterator<?> iter = aliases.iterator(); iter.hasNext();) {
    			String alias = (String) iter.next();
    			List<?> values = fetchResp.getAttributeValues(alias);
    			if (values.size() > 0) {
    				String[] arr = new String[values.size()];
    				values.toArray(arr);
    				attributes.put(alias, StringUtils.join(arr));
    			}
    		}
    		httpReq.setAttribute("attributes", attributes);
    	}
    }
	
}
