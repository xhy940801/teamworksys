package com.xzx.teamsys.dao.jdbcimpl;

import java.sql.SQLException;

import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.TransactionManager;
import com.xzx.teamsys.util.StatementFactory;

public class TransactionManagerJDBCImpl implements TransactionManager
{

	@Override
	public void startTrans()
	{
		try
		{
			StatementFactory.getConnection().setAutoCommit(false);
		}
		catch (SQLException e)
		{
			throw new DAOException("start transaction error!", e);
		}
	}

	@Override
	public void commit()
	{
		try
		{
			StatementFactory.getConnection().commit();
		}
		catch (SQLException e)
		{
			throw new DAOException("commit error!", e);
		}
	}

	@Override
	public void rollback()
	{
		try
		{
			StatementFactory.getConnection().rollback();
		}
		catch (SQLException e)
		{
			throw new DAOException("rollback error!", e);
		}
	}

}
