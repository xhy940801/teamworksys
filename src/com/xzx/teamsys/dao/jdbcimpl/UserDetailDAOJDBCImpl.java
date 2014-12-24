package com.xzx.teamsys.dao.jdbcimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.xzx.teamsys.bean.UserDetail;
import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.UserDetailDAO;
import com.xzx.teamsys.entity.Sex;

public class UserDetailDAOJDBCImpl extends JDBCBaseDAO implements UserDetailDAO
{

	@Override
	public int save(UserDetail userDetail)
	{
		String sql = "INSERT INTO user_details (user_id, nickname, sex) VALUES (?,?,?)";
		PreparedStatement statement = this.preparedStatementForCreate(sql);
		try
		{
			statement.setInt(1, userDetail.getUserId());
			if(userDetail.getNickname() != null)
				statement.setString(2, userDetail.getNickname());
			else
				statement.setNull(2, Types.VARCHAR);
			if(userDetail.getSex() != null)
				statement.setString(3, userDetail.getSex().toString());
			else
				statement.setNull(3, Types.VARCHAR);
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
	public int update(UserDetail userDetail)
	{
		String sql = "UPDATE user_details SET user_id=?, nickname=?, sex=? WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, userDetail.getUserId());
			if(userDetail.getNickname() != null)
				statement.setString(2, userDetail.getNickname());
			else
				statement.setNull(2, Types.VARCHAR);
			if(userDetail.getSex() != null)
				statement.setString(3, userDetail.getSex().toString());
			else
				statement.setNull(3, Types.VARCHAR);
			statement.setInt(4, userDetail.getId());
			return statement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public UserDetail getUserDetailById(int id)
	{
		String sql = "SELECT user_id, nickname, sex FROM user_details WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			UserDetail userDetail = null;
			if(rs.next())
			{
				userDetail = new UserDetail();
				userDetail.setId(rs.getInt("id"));
				userDetail.setUserId(rs.getInt("user_id"));
				userDetail.setNickname(rs.getString("nickname"));
				userDetail.setSex(Sex.valueOf(rs.getString("sex")));
			}
			return userDetail;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public UserDetail getUserDetailByUserId(int userId)
	{
		String sql = "SELECT id, user_id, nickname, sex FROM user_details WHERE user_id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			UserDetail userDetail = null;
			if(rs.next())
			{
				userDetail = new UserDetail();
				userDetail.setId(rs.getInt("id"));
				userDetail.setUserId(rs.getInt("user_id"));
				userDetail.setNickname(rs.getString("nickname"));
				userDetail.setSex(Sex.valueOf(rs.getString("sex")));
			}
			return userDetail;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

}
