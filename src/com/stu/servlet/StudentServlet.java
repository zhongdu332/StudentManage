package com.stu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stu.core.StringUtils;
import com.stu.dao.ScoreDao;
import com.stu.dao.StudentDao;
import com.stu.model.MessageBean;
import com.stu.model.PageBean;
import com.stu.model.StudentBean;
import com.stu.service.StudentService;

/**
 * 学生信息Servlet
 * 
 * @author 梁钊伟、曹强、胡代鑫、邹家华
 * 
 */
public class StudentServlet extends HttpServlet {

	public StudentServlet() {
		super();
	}

	/**
	 * 处理GET请求
	 * 
	 * @author 邹家华
	 * 
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
	 * 处理POST请求
	 * 
	 * @author 邹家华
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String cmd = request.getParameter("cmd");
		
		if ("add".equals(cmd)) {
			// 添加学生信息
			AddStudent(request, response);
		} else if ("stulist".equals(cmd)) {
			// 查询学生信息
			QueryStudents(request, response);
		} else if("json_stulist".equals(cmd)) {
			//根据姓名查询学生信息
			QueryStudentToJson(request,response);
		} else if("deletestudent".equals(cmd)){
			// 删除学生信息
			DeleteStudent(request, response);
		}
	}
	
	/**
	 * 删除学生信息
	 * 
	 * @author 邹家华
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置响应内容类型
        response.setContentType("application/json");
        
		// 学生标识ID
		int stuid = Integer.parseInt(request.getParameter("stuid") != null ? request.getParameter("stuid") : "0");

		//json 数据字符串
		StringBuffer sb = new StringBuffer();

		// 实例化学生数据库操作类
		StudentService studentService = new StudentDao();
		
		// 执行数据删除
		boolean result = studentService.deleteStudent(stuid);
		
		//判断学生删除结果
		if(result == true){
			sb.append("{\"code\":1,\"msg\":\"删除成功！\"}");
		}else {
			sb.append("{\"code\":0,\"msg\":\"删除失败！\"}");
		}
		
        //返回json
        PrintWriter out = response.getWriter();
        out.write(sb.toString());
        out.flush();
        out.close();
	}
		
	/**
	 * 查询学生信息
	 * 
	 * @author 曹强、梁钊伟
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void QueryStudents(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// 设置响应内容类型
		response.setContentType("text/html");

		// 页码，默认页码是1
		int currentPage = Integer.parseInt(request.getParameter("currentPage") != null ? request.getParameter("currentPage") : "1");
		// 页大小，默认页大小是10
		int pageSize = Integer.parseInt(request.getParameter("pageSize") != null ? request.getParameter("pageSize") : "10");

		// 对不合法的页码和页大小重新赋值
		if (currentPage < 1) {
			currentPage = 1;// 默认页码是1
		}
		if (pageSize < 10) {
			pageSize = 10;// 默认页大小是10
		}

		//取出查询参数,如果没有提交查询参数,则值为空字符串
		String stuName = (request.getParameter("stuName") != null ? request.getParameter("stuName") : "").trim();
		String stuNo = (request.getParameter("stuNo") != null ? request.getParameter("stuNo") : "").trim();
		
		if(!stuName.isEmpty()){
			stuName = StringUtils.toChinese(stuName);
		}
		if(!stuNo.isEmpty()){
			stuNo = StringUtils.toChinese(stuNo);
		}
		
		// 初始化查询参数类
		StudentBean studentBean = new StudentBean();
		studentBean.setStuName(stuName);// 姓名
		studentBean.setStuNo(stuNo);// 学号

		// 实例化学生数据库操作类
		StudentService studentService = new StudentDao();
		
		// 执行数据查询
		PageBean<StudentBean> pageBean = studentService.getStudents(studentBean, currentPage,pageSize);

		// 向页面传递数据
		request.setAttribute("pageBean", pageBean);//分页数据对象
		request.setAttribute("stuName", stuName);//姓名查询条件
		request.setAttribute("stuNo", stuNo);//学号查询条件

		// 请求回发
		request.getRequestDispatcher("/views/search_student.jsp").forward(request, response);
	}

	/**
	 * 根据姓名查询学生信息，添加成绩的时候用
	 * 【只选10个出来】
	 * 
	 * @author 胡代鑫
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void QueryStudentToJson(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// 设置响应内容类型
        response.setContentType("application/json");

		//取出查询参数,如果没有提交查询参数,则值为空字符串
		String stuName = (request.getParameter("query") != null ? request.getParameter("query") : "").trim();

		// 实例化学生数据库操作类
		StudentService studentService = new StudentDao();
		
		// 执行数据查询
		List stuList = studentService.getStudents(StringUtils.toUTF8(stuName));//ajax post 提交过来的是utf8编码，晕
		
		//json 数据字符串
		StringBuffer sb = new StringBuffer();

		//定义用于jquery autocomplete插件用的json字符串
		sb.append("{\"query\": \"" + stuName + "\",\"suggestions\":[");
		try {
			if(stuList != null)
			{
				for (int i = 0; i < stuList.size(); i++) {

					//转换成学生实体
					StudentBean studentBean = (StudentBean)stuList.get(i);
					
					//取出学生实体中的字段值
					sb.append("{");//json字符串开头
					sb.append(String.format("\"value\":\"%s\"," , studentBean.getStuName()));//姓名
					sb.append(String.format("\"data\":\"%s\"" , studentBean.getStuNo()));//学号
					sb.append(String.format("%s%s" ,"}" , (i < stuList.size() -1 ? "," : "")));//json 字符串结尾拼接
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		sb.append("]}");
		
        //返回json
        PrintWriter out = response.getWriter();
        out.write(sb.toString());
        out.flush();
        out.close();
	}
	
	/**
	 * 添加学生
	 * 
	 * @author 胡代鑫
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void AddStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 设置响应内容类型
		response.setContentType("text/html");

		// 获取页面提交的参数
		String stuName = request.getParameter("stuName");
		String stuNo = request.getParameter("stuNo");
		String cno = request.getParameter("cno");
		String gno = request.getParameter("gno");
		String stuAge = request.getParameter("stuAge");
		String stuAddr = request.getParameter("stuAddr");
		String stuSex = request.getParameter("stuSex");

		// 参数检查
		// 空或空字符判断,不合法姓名
		if (stuName == null || "".equals(stuName)) {
			
			//设置页面提示消息
			request.setAttribute("msg", new MessageBean(0, "输入的姓名不合法，添加失败！"));
			//请求回发
			request.getRequestDispatcher("/views/add_student.jsp").forward(request, response);
			
			return;
		}
		// 空或字符判断,不合法学号
		if (stuNo == null || "".equals(stuNo)) {
			
			//设置页面提示消息
			request.setAttribute("msg", new MessageBean(0, "输入的学号不合法，添加失败！"));
			//请求回发
			request.getRequestDispatcher("/views/add_student.jsp").forward(request, response);
			
			return;
		}
		// 空或正则判断非数字,不合法年龄值
		if (stuAge == null || Pattern.matches("[^\\d]*", stuAge)) {
			
			//设置页面提示消息
			request.setAttribute("msg", new MessageBean(0, "输入的年龄不合法，添加失败！"));
			//请求回发
			request.getRequestDispatcher("/views/add_student.jsp").forward(request, response);
			
			return;
		}
		// 空或空字符判断,不合法性别
		if (stuSex == null || "".equals(stuSex)) {
			
			//设置页面提示消息
			request.setAttribute("msg", new MessageBean(0, "输入的性别不合法，添加失败！"));
			//请求回发
			request.getRequestDispatcher("/views/add_student.jsp").forward(request, response);
			
			return;
		}

		// 实例化学生信息类,并从请求中获取对应参数值
		StudentBean studentBean = new StudentBean();
		studentBean.setStuName(stuName);// 姓名,转换了参数的编码为GBK，不转会是乱码。
		studentBean.setStuNo(stuNo);// 学号
		studentBean.setCno(cno);// 班级编号,转换了参数的编码为GBK，不转会是乱码。
		studentBean.setGno(gno);// 年级编号,转换了参数的编码为GBK，不转会是乱码。
		studentBean.setStuAge(Integer.parseInt(stuAge));// 年龄
		studentBean.setStuAddr(stuAddr);// 住址,转换了参数的编码为GBK，不转会是乱码。
		studentBean.setStuSex(stuSex);// 性别,转换了参数的编码为GBK，不转会是乱码。
		
		//检查学号是否有重复
		boolean stuNoExists = new StudentDao().checkExists(stuNo);
		if(stuNoExists == true){
			request.setAttribute("msg", new MessageBean(1, "添加失败，学号重复！"));
		} else {
			// 执行数据库添加
			boolean result = new StudentDao().addStudent(studentBean);
			
			// 根据数据库返回结果设置页面提示消息
			if (result == true) {
				request.setAttribute("msg", new MessageBean(1, "添加成功！"));
			} else {
				request.setAttribute("msg", new MessageBean(0, "添加失败！"));
			}
		}
		// 请求回发
		request.getRequestDispatcher("/views/add_student.jsp").forward(request,response);
	}

}
