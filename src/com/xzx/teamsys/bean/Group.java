package com.xzx.teamsys.bean;

import com.xzx.teamsys.entity.Editable;

public class Group
{
	private int id;
	private int projectId;
	private String name;
	private String remark;
	private Editable editable;
	
	public Group()
	{
		
	}
	
	public Group(int projectId, String name, String remark, Editable editable)
	{
		this.projectId = projectId;
		this.name = name;
		this.remark = remark;
		this.editable = editable;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
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

	public Editable getEditable()
	{
		return editable;
	}

	public void setEditable(Editable editable)
	{
		this.editable = editable;
	}
	
}
