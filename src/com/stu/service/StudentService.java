package com.stu.service;

import java.util.List;

import com.stu.model.PageBean;
import com.stu.model.StudentBean;

/***
 * 学生信息Service
 * @author 胡代鑫、邹家华、曹强、梁钊伟
 */
public interface StudentService {
	/***
	 * 添加学生信息
	 * 
	 * @param model
	 * @return
	 */
	boolean addStudent(StudentBean model);

	/**
	 * 查询学生信息
	 * 
	 * @author 曹强、梁钊伟
	 * 
	 * @param model 查询条件
	 * @param currentPage 当有页码
	 * @param pageCount 页大小
	 * @return
	 */
	PageBean<StudentBean> getStudents(StudentBean model,int currentPage,int pageCount);
	
	/**
	 * 根据姓名查询学生信息【只选10个出来】
	 * 
	 * @author 胡代鑫
	 * 
	 * @param stuName 姓名
	 * @return
	 */
	List getStudents(String stuName);
	
	/**
	 * 删除学生信息
	 * 
	 * @author 邹家华
	 * 
	 * @param stuid
	 * @return
	 */
	boolean deleteStudent(int stuid);
	
	/**
	 * 检查学号是否存在
	 * 
	 * @author 胡代鑫
	 * 
	 * @param stuNo
	 * @return
	 */
	boolean checkExists(String stuNo);
}
