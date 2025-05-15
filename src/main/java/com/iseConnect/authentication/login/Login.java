package com.iseConnect.authentication.login;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.iseConnect.model.User;

import java.io.IOException;
import java.io.PrintWriter;




@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String uname = request.getParameter("email");
		String pass = request.getParameter("password");
		String designation = request.getParameter("designation");
		
		LoginDAO lDao = new LoginDAO();
		
		HttpSession session = request.getSession();
		if( lDao.check(uname, pass,designation)) {
			User user = new User();

			user = lDao.getUserDetails(uname,designation);
			session.setAttribute("name", user.getName());
			session.setAttribute("user", user);
			
			response.sendRedirect("home.jsp");
		}else {
			out.print("<script>" +
		            "alert('Authentication Failed!');" +
		            "window.location.href = 'index.html';" + 
		            "</script>");
		}
	}

}