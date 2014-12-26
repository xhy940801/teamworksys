package com.xzx.teamsys.entity;

import com.xzx.teamsys.bean.Project;
import com.xzx.teamsys.bean.UserDetail;

public class ProjectInfo extends Project
{
	private int id;
	private int owner;
	private String name;
	private String remark;
	private int defaultGroupId;
	private String nickname;
	
	public ProjectInfo()
	{
		
	}
	
	public ProjectInfo(Project project, UserDetail userDetail)
	{
		this.setId(project.getId());
		this.setName(project.getName());
		this.setOwner(project.getOwner());
		this.setRemark(project.getRemark());
		this.setDefaultGroupId(project.getDefaultGroupId());
		this.setNickname(userDetail.getNickname());
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

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	
}