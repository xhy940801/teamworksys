package com.xzx.teamsys.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.util.StatementFactory;

public class JDBCBaseDAO
{
	protected PreparedStatement preparedStatement(String sql)
	{
		Connection conn;
		PreparedStatement statement;
		try
		{
			conn = StatementFactory.getConnection();
		}
		catch (SQLException e)
		{
			throw new DAOException("get connection error", e);
		}
		try
		{
			statement = conn.prepareStatement(sql);
		}
		catch (SQLException e)
		{
			throw new DAOException("create statement error", e);
		}
		return statement;
	}

	protected PreparedStatement preparedStatement(String sql, String[] columnNames)
	{
		Connection conn;
		PreparedStatement statement;
		try
		{
			conn = StatementFactory.getConnection();
		}
		catch (SQLException e)
		{
			throw new DAOException("get connection error", e);
		}
		try
		{
			statement = conn.prepareStatement(sql, columnNames);
		}
		catch (SQLException e)
		{
			throw new DAOException("create statement error", e);
		}
		return statement;
	}
}
