package com.xzx.teamsys.dao;

import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.bean.User;
import com.xzx.teamsys.entity.ContributorStatus;

public interface UserDAO
{
	/**
	 * 新增一条User记录
	 * @param user user记录（忽略里面的id属性）当新增完后把id写回user里
	 * @return 返回新的记录的id
	 */
	public int save(User user);
	
	/**
	 * 更新User记录
	 * @param user 更新的记录（根据里面的id的值来确定更新那条记录）
	 * @return 影响的记录数（一般是1，如果更新前后没有分别就返回0（可以直接返回jdbc的那个返回））
	 */
	public int update(User user);
	
	/**
	 * 通过id获取User
	 * @param id id
	 * @return User
	 */
	public User getUserById(int id);
	
	/**
	 * 通过email来获取User
	 * @param email email
	 * @return User
	 */
	public User getUserByEmail(String email);
	
	/**
	 * 通过projectId来获取参与project的所有用户
	 * @param id project的id
	 * @param status 接受状态
	 * @return 所有用户
	 */
	public List<User> getContributorByProjectId(int projectId, ContributorStatus status);
	
	/**
	 * 通过projectId来获取参与project的所有用户
	 * @param id project的id
	 * @param statuses 接受状态
	 * @return 所有用户
	 */
	public List<User> getContributorByProjectId(int projectId, EnumSet<ContributorStatus> statuses);
}
