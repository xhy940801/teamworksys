package com.xzx.teamsys.dao;

import com.xzx.teamsys.bean.UserDetail;

public interface UserDetailDAO
{ 
	/**
	 * 新增用户详细信息
	 * @param userDetail 用户详细信息（忽略里面的id属性）当新增完后把id写回user里
	 * @return 返回新的记录的id
	 */
	public int save(UserDetail userDetail);
	
	/**
	 * 更新用户详细信息
	 * @param userDetail 更新的记录（根据里面的id的值来确定更新那条记录）
	 * @return 影响的记录数（一般是1，如果更新前后没有分别就返回0（可以直接返回jdbc的那个返回））
	 */
	public int update(UserDetail userDetail);
	
	/**
	 * 获取用户详细信息
	 * @param id UserDetail的id
	 * @return 用户详细信息
	 */
	public UserDetail getUserDetailById(int id);
	
	/**
	 * 获取用户详细信息
	 * @param userId 用户id
	 * @return 用户详细信息
	 */
	public UserDetail getUserDetailByUserId(int userId);
}
