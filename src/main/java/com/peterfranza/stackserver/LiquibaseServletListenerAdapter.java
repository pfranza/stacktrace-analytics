package com.peterfranza.stackserver;

import javax.servlet.ServletContextEvent;

import liquibase.integration.servlet.LiquibaseServletListener;

public class LiquibaseServletListenerAdapter extends LiquibaseServletListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!!!!!!! LiquibaseServletListenerAdapter::contextInitialized");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		super.contextInitialized(servletContextEvent);
	}
	
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!!!!!!! LiquibaseServletListenerAdapter::contextDestroyed");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		super.contextDestroyed(servletContextEvent);
	}
}

