package com.xzx.teamsys.entity;

import java.text.SimpleDateFormat;

import com.xzx.teamsys.entity.CompletionStatus;

public class Task
{
	private int id;
	private int userId;
	private int projectId;
	private String name;
	private String remark;
	private CompletionStatus status;
	private java.util.Date created;
	private java.util.Date deadline;
	private String debriefing;
	
	public Task()
	{
		
	}
	
	public Task(com.xzx.teamsys.bean.Task task)
	{
		this.id = task.getId();
		this.userId = task.getUserId();
		this.projectId = task.getProjectId();
		this.name = task.getName();
		this.remark = task.getRemark();
		this.status = task.getStatus();
		this.created = new java.util.Date(task.getCreated().getTime());
		this.deadline = new java.util.Date(task.getDeadline().getTime());
		this.debriefing = task.getDebriefing();
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
	
	public java.util.Date getCreated()
	{
		return created;
	}
	
	public void setCreated(java.util.Date created)
	{
		this.created = new java.util.Date(created.getTime());
	}
	
	public java.util.Date getDeadline()
	{
		return deadline;
	}
	
	public String getSDeadline()
	{
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy年MM月dd日");
		return dateformat.format(deadline);
	}
	
	public void setDeadline(java.util.Date deadline)
	{
		this.deadline = new java.util.Date(deadline.getTime());
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
