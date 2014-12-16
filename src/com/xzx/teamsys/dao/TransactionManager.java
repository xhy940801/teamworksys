package com.xzx.teamsys.dao;

public interface TransactionManager
{
	public void startTrans();
	public void commit();
	public void rollback();
}
