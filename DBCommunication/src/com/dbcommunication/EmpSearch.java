package com.dbcommunication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EmpSearch extends HttpServlet {

	/* Copyright Tekcel (tekcelinc.com) */
	
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	
	public void init()
	{
		ServletConfig cfg = getServletConfig();
		try {
			//load the driver
			Class.forName(cfg.getInitParameter("driverName"));
			//DB URL
			String dbURL = cfg.getInitParameter("URL");
			String uname = cfg.getInitParameter("userName");
			String pwd =  cfg.getInitParameter("password");
			//establish the connection
			con =  DriverManager.getConnection(dbURL,uname,pwd);
			System.out.println("Connection Established");
		} catch (Exception e) {
			System.out.println("Connect Not Established");
			e.printStackTrace();
		}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Inside Get Method");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//capture the client data
		int eno = Integer.parseInt(request.getParameter("t1"));
		//Input SQL
		
		String sql = "SELECT * FROM EMP WHERE EMPNO = "+eno;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next())
			{
				out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getInt(3)+"\t"+rs.getString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy()
	{
		try {
			if(rs != null)
				rs.close();
			if(st != null)
				st.close();
			if(con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside Post Method");
		doGet(request, response);
	}

}
