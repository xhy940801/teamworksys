package com.xzx.teamsys.bean;

import com.xzx.teamsys.entity.ContributorStatus;

public class ProjectUserLinker
{
	private int id;
	private int userId;
	private int projectId;
	private ContributorStatus status;
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getUserId()
	{
		return userId;
	}
	
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	
	public int getProjectId()
	{
		return projectId;
	}
	
	public void setProjectId(int projectId)
	{
		this.projectId = projectId;
	}
	
	public ContributorStatus getCondition()
	{
		return status;
	}
	
	public void setCondition(ContributorStatus status)
	{
		this.status = status;
	}
	
}
