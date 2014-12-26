package com.xzx.teamsys.dao.jdbcimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.bean.User;
import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.UserDAO;
import com.xzx.teamsys.entity.ContributorStatus;

public class UserDAOJDBCImpl extends JDBCBaseDAO implements UserDAO
{

	@Override
	public int save(User user)
	{
		String sql = "INSERT INTO users (email, password) values(?,?)";
		PreparedStatement statement = this.preparedStatementForCreate(sql);
		try
		{
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next())
				return rs.getInt(1);
			else
				throw new DAOException("unknow error");
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public int update(User user)
	{
		String sql = "UPDATE users set email=?, password=? WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getId());
			return statement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public User getUserById(int id)
	{
		String sql = "SELECT id, email, password FROM users where id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next())
			{
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				return user;
			}
			else
				return null;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public User getUserByEmail(String email)
	{
		String sql = "SELECT id, email, password FROM users where email=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			if (rs.next())
			{
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				return user;
			}
			else
				return null;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public List<User> getContributorByProjectId(int projectId,
			ContributorStatus status)
	{
		return this.getContributorByProjectId(projectId, EnumSet.of(status));
	}

	@Override
	public List<User> getContributorByProjectId(int projectId,
			EnumSet<ContributorStatus> statuses)
	{
		String sql = "SELECT users.id, users.email, users.password FROM projects_users LEFT JOIN users ON projects_users.user_id = users.id WHERE projects_users.project_id=? AND status IN (?,?,?)";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, projectId);
			int i = 2;
			for (ContributorStatus status : statuses)
				statement.setString(i++, status.toString());
			while (i < 5)
				statement.setNull(i++, Types.VARCHAR);
			ResultSet rs = statement.executeQuery();
			List<User> users = new ArrayList<User>();
			while (rs.next())
			{
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				users.add(user);
			}
			return users;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public List<User> getContributorByGroupId(int groupId,
			ContributorStatus status)
	{
		return this.getContributorByGroupId(groupId, EnumSet.of(status));
	}

	@Override
	public List<User> getContributorByGroupId(int groupId,
			EnumSet<ContributorStatus> statuses)
	{
		String sql = "SELECT users.id users.email, users.password FROM groups_users LEFT JOIN users ON groups_users.user_id = users.id WHERE groups_users.group_id=? AND status IN (?,?,?)";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, groupId);
			int i = 2;
			for (ContributorStatus status : statuses)
				statement.setString(i++, status.toString());
			while (i < 5)
				statement.setNull(i++, Types.VARCHAR);
			ResultSet rs = statement.executeQuery();
			List<User> users = new ArrayList<User>();
			while (rs.next())
			{
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				users.add(user);
			}
			return users;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}
}
