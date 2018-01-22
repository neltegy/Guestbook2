package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.vo.GuestBookVo;


@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionName = request.getParameter("a");
		
		if("list".equals(actionName)) {
			System.out.println("list 진입");
			
			GuestBookDao dao = new GuestBookDao();
			List<GuestBookVo> list = dao.getlist();
			
			request.setAttribute("list", list);
			
			RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
			rd.forward(request, response);
		}else if("add".equals(actionName)) {
			System.out.println("add 진입");
			
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			
			GuestBookVo vo = new GuestBookVo(name,password,content);
			GuestBookDao dao = new GuestBookDao();
			dao.insert(vo);
			
			response.sendRedirect("gb?a=list");
		}else if("deleteform".equals(actionName)) {
			System.out.println("deleteform 진입");
			
			String no = request.getParameter("no");
			request.setAttribute("no", no);
			
			RequestDispatcher rd = request.getRequestDispatcher("deleteform.jsp");
			rd.forward(request, response);
			
		}else if("delete".equals(actionName)) {
			System.out.println("delete 진입");
			
			String no = request.getParameter("no");
			String password = request.getParameter("password");
			
			int no2 = Integer.parseInt(no);
			
			GuestBookDao dao = new GuestBookDao();
			
			List<GuestBookVo> gblist = dao.getlist();
			for(GuestBookVo str : gblist){
				if(str.getNo() == no2){
					if(str.getPassword().equals(password)){
						dao.delete(no2);
					}
				}
				
			}
			//no 랑 password가 맞으면 삭제가능

			response.sendRedirect("gb?a=list");
			
		}else {
			System.out.println("잘못입력하셨습니다.");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
