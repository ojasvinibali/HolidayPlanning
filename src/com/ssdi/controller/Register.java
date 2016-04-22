package com.ssdi.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.ssdi.POJO.userbean;
import com.ssdi.model.databaseFactory;
import com.ssdi.model.ServicesDao;

/**
 * Servlet implementation class Register
 */

@WebServlet("/Register")

public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ServicesDao serviceDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		ServletContext context = getServletContext();
		databaseFactory factory = databaseFactory.getInstance(context.getInitParameter("environment"));
		serviceDao = factory.createServiceDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		userbean user = new userbean();

		try {
			user.setUsername(request.getParameter("username"));
			user.setEmail(request.getParameter("email"));
			user.setPassword(request.getParameter("password1"));
			String email = user.getEmail();
			
			boolean exist = ServicesDao.checkEmail(email);
			
			if (exist) {
				RequestDispatcher rd = request.getRequestDispatcher("/RegisterPageError.jsp");
				rd.forward(request, response);
			} else {
				user = serviceDao.registerUser(user);
				request.setAttribute("username", email);

				HttpSession Session = request.getSession();
				Session.setAttribute("username", user.getEmail());

				RequestDispatcher rd = request.getRequestDispatcher("/RegisterRedirect.jsp");
				rd.forward(request, response);
			}
		} catch (Throwable theException) {
			System.out.println(theException);
		}
	}
}