package com.xzx.teamsys.service;

import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.entity.ContributorStatus;
import com.xzx.teamsys.entity.UserProjectInfo;

public interface ProjectService extends WebService
{
	public int createProject(int owner, String name, String remark);
	public int deleteProject(int projectId);
	public List<UserProjectInfo> getUserProjectInfos(int owner, ContributorStatus status);
	public List<UserProjectInfo> getUserProjectInfos(int owner, EnumSet<ContributorStatus> status);
	public int inviteUser(int userId);
	public int accept(int userId, int projectId);
	public int refuse(int userId, int projectId);
}
