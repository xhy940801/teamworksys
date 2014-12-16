package com.xzx.teamsys.dao.jdbcimpl;

import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.bean.User;
import com.xzx.teamsys.dao.UserDAO;
import com.xzx.teamsys.entity.ContributorStatus;

public class UserDAOJDBCImpl implements UserDAO
{

	@Override
	public int save(User user)
	{
		return 0;
	}

	@Override
	public int update(User user)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getUserById(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByEmail(String email)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getContributorByProjectId(int id,
			ContributorStatus status)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getContributorByProjectId(int id,
			EnumSet<ContributorStatus> statuses)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
