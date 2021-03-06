package com.stu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.stu.core.DB;
import com.stu.model.AdminBean;
import com.stu.service.AdminService;

/**
 * 管理员数据库操作类
 * 
 * @author 夏宇航
 * 
 */
public class AdminDao implements AdminService {

	// 实例化数据库访问对象
	DB db = new DB();

	/**
	 * 用户登录
	 * 
	 * @author 夏宇航
	 * 
	 */
	public AdminBean adminLogin(AdminBean model) {
		AdminBean returnModel = null;

		// SQL语句
		String sql = "select * from t_admin where uAccounts=? and uPassword=?";

		// 设置SQL语句参数
		Object[] params = { model.getUAccounts(), model.getUPassword() };

		try {

			// 执行SQL查询
			db.doPstm(sql, params);

			// 获取结果集
			ResultSet rs = db.getRs();

			if (rs != null) {
				// 实例化管理员
				returnModel = new AdminBean();

				while (rs.next()) {
					// 从结果集中读取，并设置管理员实体数据
					returnModel.setUid(rs.getInt("uid"));// 设置管理员标识ID
					returnModel.setUName(rs.getString("uName"));// 设置管理员姓名
					returnModel.setUAccounts(rs.getString("uAccounts"));// 设置管理员账号
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放数据库链接
			db.close();
		}

		return returnModel;
	}

	/**
	 * 修改密码
	 * 
	 * @author 夏宇航
	 * 
	 */
	public boolean changePassword(int uid, String uPassword, String newUPassword) {

		// 待执行SQL
		String sql = "update t_admin set uPassword = ? where uid = ? and uPassword = ?";

		// SQL参数
		Object[] params = { newUPassword, uid, uPassword };

		try {

			// 执行数据库更新
			db.doPstm(sql, params);

			// 获取影响行数
			int rowCount = db.getCount();

			// 结果>0表示成功
			return (rowCount > 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放数据库链接
			db.close();
		}

		return false;
	}

}
