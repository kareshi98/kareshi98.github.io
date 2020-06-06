package cn.techaction.dao;

import cn.techaction.pojo.User;

public interface ActionUserDao {

	/**
	 * 根据用户名查找用户
	 * @param account
	 * @return
	 */
	public int checkUserByAccount(String account);
	/**
	 * 根据用户名和密码查找用户
	 * @param account
	 * @param password
	 * @return
	 */
	public User findUserByAccountAndPassword(String account,String password);
}
