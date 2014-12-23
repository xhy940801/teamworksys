package com.xzx.teamsys.service.defaultimpl;

import com.xzx.teamsys.bean.User;
import com.xzx.teamsys.bean.UserDetail;
import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.TransactionManager;
import com.xzx.teamsys.dao.UserDAO;
import com.xzx.teamsys.dao.UserDetailDAO;
import com.xzx.teamsys.entity.Sex;
import com.xzx.teamsys.entity.UserInfo;
import com.xzx.teamsys.service.UserService;
import com.xzx.teamsys.util.MD5Util;

public class UserServiceDefaultImpl extends WebServiceDefaultImpl implements UserService
{
	private TransactionManager transactionManager;
	private UserDAO userDAO;
	private UserDetailDAO userDetailDAO;

	@Override
	public int register(String email, String password)
	{
		if(email.length() > 32)
		{
			lastError = "EMAIL长度过长，长度必须小于32位";
			return -1;
		}
		User user = new User(email, MD5Util.md5(password));
		UserDetail userDetail = new UserDetail();
		try
		{
			transactionManager.startTrans();
			int userId = userDAO.save(user);
			userDetail.setUserId(userId);
			userDetailDAO.save(userDetail);
			transactionManager.commit();
			return userId;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			transactionManager.rollback();
			return -1;
		}
	}
	
	@Override
	public int changePassword(int id, String oldPwd, String newPwd)
	{
		try
		{
			User user = userDAO.getUserById(id);
			if(user == null)
			{
				lastError = "内部错误";
				return -1;
			}
			if(user.getPassword() != MD5Util.md5(oldPwd))
			{
				lastError = "旧密码错误";
				return -1;
			}
			user.setPassword(MD5Util.md5(newPwd));
			return 1;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return -1;
		}
	}

	@Override
	public int checkPassword(String email, String password)
	{
		try
		{
			User user = userDAO.getUserByEmail(email);
			if(user == null || user.getPassword() != MD5Util.md5(password))
			{
				lastError = "用户名或密码错误";
				return -1;
			}
			else
				return 1;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return -1;
		}
	}

	@Override
	public int updateUserDetail(int userId, String nickname, Sex sex)
	{
		try
		{
			UserDetail userDetail = userDetailDAO.getUserDetailByUserId(userId);
			if(userDetail == null)
			{
				lastError = "内部错误";
				return -1;
			}
			userDetail.setNickname(nickname);
			userDetail.setSex(sex);
			userDetailDAO.update(userDetail);
			return 1;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return -1;
		}
	}

	@Override
	public UserInfo getUserInfo(int id)
	{
		try
		{
			User user = userDAO.getUserById(id);
			UserDetail userDetail = userDetailDAO.getUserDetailByUserId(id);
			if(user == null || userDetail == null)
			{
				lastError = "用户不存在";
				return null;
			}
			return new UserInfo(user, userDetail);
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return null;
		}
	}

	public void setUserDAO(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	public void setUserDetailDAO(UserDetailDAO userDetailDAO)
	{
		this.userDetailDAO = userDetailDAO;
	}
	

}
