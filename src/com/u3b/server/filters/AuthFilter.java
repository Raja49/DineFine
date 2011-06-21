package com.u3b.server.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthFilter implements Filter {

	FilterConfig filterCfg;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		System.out.println("Passando pelo filtro...");
		
		UserService userService = UserServiceFactory.getUserService();

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String thisURL = request.getRequestURI();

		if (request.getUserPrincipal() != null) {
/*			response.getWriter().println(
					"<p>Hello, " + request.getUserPrincipal().getName()
							+ "!  You can <a href=\""
							+ userService.createLogoutURL(thisURL)
							+ "\">sign out</a>.</p>");*/
			chain.doFilter(request, response);
		} else {
			/*
			 * response.getWriter().println("<p>Please <a href=\"" +
			 * userService.createLoginURL(thisURL) + "\">sign in</a>.</p>");
			 */
			response.sendRedirect(userService.createLoginURL(thisURL));
			System.out.println("Usuário não autenticado!");
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterCfg = arg0;
	}

}
