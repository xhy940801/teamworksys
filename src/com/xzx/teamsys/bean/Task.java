package com.xzx.teamsys.bean;

import com.xzx.teamsys.entity.CompletionStatus;

public class Task
{
	private int id;
	private int userId;
	private int projectId;
	private String name;
	private String remark;
	private CompletionStatus status;
	private java.sql.Date created;
	private java.sql.Date deadline;
	private String debriefing;
	
	public Task()
	{
		
	}
	
	public Task(int userId, int projectId, String name, String remark, CompletionStatus status, java.util.Date created, java.util.Date deadline, String debriefing)
	{
		this.userId = userId;
		this.projectId = projectId;
		this.name = name;
		this.remark = remark;
		this.status = status;
		this.created = new java.sql.Date(created.getTime());
		this.deadline = new java.sql.Date(deadline.getTime());
		this.debriefing = debriefing;
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
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getRemark()
	{
		return remark;
	}
	
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
	public CompletionStatus getStatus()
	{
		return status;
	}

	public void setStatus(CompletionStatus status)
	{
		this.status = status;
	}
	
	public java.sql.Date getCreated()
	{
		return created;
	}
	
	public void setCreated(java.util.Date created)
	{
		this.created = new java.sql.Date(created.getTime());
	}
	
	public java.sql.Date getDeadline()
	{
		return deadline;
	}
	
	public void setDeadline(java.util.Date deadline)
	{
		this.deadline = new java.sql.Date(deadline.getTime());
	}

	public String getDebriefing()
	{
		return debriefing;
	}

	public void setDebriefing(String debriefing)
	{
		this.debriefing = debriefing;
	}
	
}
