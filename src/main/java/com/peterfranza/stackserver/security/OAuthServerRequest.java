package com.peterfranza.stackserver.security;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.core.HttpRequestContext;
import com.sun.jersey.oauth.signature.OAuthRequest;

public class OAuthServerRequest implements OAuthRequest {

    private HttpRequestContext context;

    private static HashSet<String> EMPTY_SET = new HashSet<String>();

    private static ArrayList<String> EMPTY_LIST = new ArrayList<String>();

    public OAuthServerRequest(HttpRequestContext context) {
        this.context = context;
    }

    @Override
    public String getRequestMethod() {
        return context.getMethod();
    }

    @Override
    public String getRequestURL() {
        try {
            return context.getRequestUri().toURL().toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(OAuthServerRequest.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static Set<String> keys(MultivaluedMap<String, String> mvm) {
        if (mvm == null) {
            return EMPTY_SET;
        }
        Set<String> s = mvm.keySet();
        if (s == null) {
            return EMPTY_SET;
        }
        return s;
    }

    private static List<String> values(MultivaluedMap<String, String> mvm, String key) {
        if (mvm == null) {
            return EMPTY_LIST;
        }
        List<String> v = mvm.get(key);
        if (v == null) {
            return EMPTY_LIST;
        }
        return v;
    }

    @Override
    public Set<String> getParameterNames() {
        HashSet<String> n = new HashSet<String>();
        n.addAll(keys(context.getQueryParameters()));
        n.addAll(keys(context.getFormParameters()));
        return n;
    }

    @Override
    public List<String> getParameterValues(String name) {
        ArrayList<String> v = new ArrayList<String>();
        v.addAll(values(context.getQueryParameters(), name));
        v.addAll(values(context.getFormParameters(), name));
        return v;
    }

    @Override
    public List<String> getHeaderValues(String name) {
        return context.getRequestHeader(name);
    }

    @Override
    public void addHeaderValue(String name, String value) throws IllegalStateException {
        throw new IllegalStateException("Modifying OAuthServerRequest unsupported");
    }

}