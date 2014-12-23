package com.xzx.teamsys.bean;

import com.xzx.teamsys.entity.ContributorStatus;

public class ProjectUserLinker
{
	private int id;
	private int userId;
	private int projectId;
	private ContributorStatus status;
	
	public ProjectUserLinker()
	{
		
	}
	
	public ProjectUserLinker(int userId, int projectId, ContributorStatus status)
	{
		this.userId = userId;
		this.projectId = projectId;
		this.status = status;
	}
	
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
	
	public ContributorStatus getStatus()
	{
		return status;
	}
	
	public void setStatus(ContributorStatus status)
	{
		this.status = status;
	}
	
}
