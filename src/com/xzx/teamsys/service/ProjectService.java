package com.xzx.teamsys.service;

import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.entity.ContributorStatus;
import com.xzx.teamsys.entity.ProjectInfo;
import com.xzx.teamsys.entity.UserInfo;
import com.xzx.teamsys.entity.UserProjectInfo;

public interface ProjectService extends WebService
{
	public int createProject(int owner, String name, String remark);
	public int deleteProject(int projectId);
	public ProjectInfo getProjectInfo(int projectId);
	public List<UserProjectInfo> getUserProjectInfos(int owner, ContributorStatus status);
	public List<UserProjectInfo> getUserProjectInfos(int owner, EnumSet<ContributorStatus> statuses);
	public List<UserInfo> getProjectUserInfos(int projectId);
	public int inviteUser(String email, int projectId);
	public int accept(int linkerId, int userId);
	public int refuse(int linkerId, int userId);
}
