package com.u3b.server;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.u3b.server.business.GTalkIntegration;

public class GtalkServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -576297455892697188L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		// Getting Locale from request
		Locale lo = req.getLocale();
		
		ResourceBundle bundle = ResourceBundle.getBundle("com.u3b.server.i18n.xmppMessages", lo);
		
		XMPPService xmpp = XMPPServiceFactory.getXMPPService();
        Message message = xmpp.parseMessage(req);

		GTalkIntegration integrator = new GTalkIntegration();
		integrator.setBundle(bundle);
		integrator.parseAndAnswer(message);
	}	
}