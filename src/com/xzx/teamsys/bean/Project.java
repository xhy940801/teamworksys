package com.xzx.teamsys.bean;

public class Project
{
	private int id;
	private int owner;
	private String name;
	private String remark;
	private int defaultGroupId;
	
	public Project()
	{
		
	}
	
	public Project(int owner, String name, String remark, int defaultGroupId)
	{
		this.owner = owner;
		this.name = name;
		this.remark = remark;
		this.defaultGroupId = defaultGroupId;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getOwner()
	{
		return owner;
	}
	
	public void setOwner(int owner)
	{
		this.owner = owner;
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

	public int getDefaultGroupId()
	{
		return defaultGroupId;
	}

	public void setDefaultGroupId(int defaultGroupId)
	{
		this.defaultGroupId = defaultGroupId;
	}
	
}
