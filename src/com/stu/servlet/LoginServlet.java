package com.stu.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.stu.core.Encrypt;
import com.stu.dao.AdminDao;
import com.stu.model.AdminBean;
import com.stu.model.MessageBean;

public class LoginServlet extends HttpServlet {

	public LoginServlet() {
		super();
	}

	/**
	 * ����POST����
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * �û��˳�
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ������Ӧ��������
		response.setContentType("text/html");

		// ���ݲ�ͬ��cmdִ�в�ͬ�Ĳ���
		String cmd = request.getParameter("cmd");
		
		if ("login".equals(cmd)) {
			//����Ա�û���¼
			AdminLogin(request, response);
		} else if("login_out".equals(cmd)) {
			//�û��˳�
            AdminLoginOut(request, response); 
		}
	}

	/**
	 * �����û���¼
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void AdminLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ʵ��������Ա�࣬ȡ����¼�����еĲ���ֵ������ֵ����Ӧ������
		AdminBean adminBean = new AdminBean();
		adminBean.setUAccounts(request.getParameter("uAccounts"));
		adminBean.setUPassword(Encrypt.encode(request.getParameter("uPassword")));

		AdminDao adminDao = new AdminDao();
		// ִ�����ݿ��ѯ��֤
		AdminBean loginBean = adminDao.adminLogin(adminBean);
		if (loginBean != null && loginBean.getUid() > 0) {
			
			// ��¼��֤�ɹ������û���Ϣд�뵽Session
			HttpSession session = request.getSession(true);  
		    
			//���ù���ʱ��Ϊ3600�루1��Сʱ��
			session.setMaxInactiveInterval(3600);
		    
			//�˺���Ϣ���浽Session
		    session.setAttribute("LoginUser",loginBean);
		    
		    //��ת����ҳ
		    request.getRequestDispatcher("/views/index.jsp").forward(request, response); 
		    
		}else{
			
			//����ҳ����ʾ��Ϣ
			request.setAttribute("ErrMsg", new MessageBean(0,"�û������������"));
			
			//��ת����ҳ
		    request.getRequestDispatcher("/views/login.jsp").forward(request, response); 
		}
	}

	/**
	 * �û��˳�
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void AdminLoginOut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("LoginUser");
		//��ת����ҳ
		request.getRequestDispatcher("/views/login.jsp").forward(request, response);
	}
}