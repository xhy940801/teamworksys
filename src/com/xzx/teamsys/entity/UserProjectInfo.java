package com.xzx.teamsys.entity;

import com.xzx.teamsys.bean.Group;
import com.xzx.teamsys.bean.Project;
import com.xzx.teamsys.bean.User;

public class UserProjectInfo
{
	private int projectId;
	private int owner;
	private String projectName;
	private String projectRemark;
	private int userId;
	private ContributorStatus status;
	private int groupId;
	private String groupName;
	private String groupRemark;
	
	public UserProjectInfo()
	{
		
	}
	
	public UserProjectInfo(int userId, Project project, Group group, ContributorStatus status)
	{
		this.projectId = project.getId();
		this.owner = project.getOwner();
		this.projectName = project.getName();
		this.projectRemark = project.getRemark();
		this.userId = userId;
		this.status = status;
		this.groupId = group.getId();
		this.groupName = group.getName();
		this.groupRemark = group.getRemark();
	}
	
	public UserProjectInfo(User user, Project project, Group group, ContributorStatus status)
	{
		this(user.getId(), project, group, status);
	}

	public int getProjectId()
	{
		return projectId;
	}

	public void setProjectId(int projectId)
	{
		this.projectId = projectId;
	}

	public int getOwner()
	{
		return owner;
	}

	public void setOwner(int owner)
	{
		this.owner = owner;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	public String getProjectRemark()
	{
		return projectRemark;
	}

	public void setProjectRemark(String projectRemark)
	{
		this.projectRemark = projectRemark;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	
	public ContributorStatus getStatus()
	{
		return status;
	}
	
	public void setStatus(ContributorStatus status)
	{
		this.status = status;
	}

	public int getGroupId()
	{
		return groupId;
	}

	public void setGroupId(int groupId)
	{
		this.groupId = groupId;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public String getGroupRemark()
	{
		return groupRemark;
	}

	public void setGroupRemark(String groupRemark)
	{
		this.groupRemark = groupRemark;
	}
}
