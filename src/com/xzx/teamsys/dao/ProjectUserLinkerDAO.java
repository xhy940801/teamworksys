package com.xzx.teamsys.dao;

import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.bean.ProjectUserLinker;
import com.xzx.teamsys.entity.ContributorStatus;

public interface ProjectUserLinkerDAO
{
	public int save(ProjectUserLinker projectUserLinker);
	public int update(ProjectUserLinker projectUserLinker);
	public int delete(int projectUserLinkerId);
	public ProjectUserLinker getLinkerById(int projectUserLinkerId);
	public ProjectUserLinker getLinkerByUserIdAndProjectId(int userId, int projectId);
	public List<ProjectUserLinker> getLinkerByProjectId(int projectId, ContributorStatus status);
	public List<ProjectUserLinker> getLinkerByProjectId(int projectId, EnumSet<ContributorStatus> statuses);
	public List<ProjectUserLinker> getLinkerByUserId(int userId, ContributorStatus status);
	public List<ProjectUserLinker> getLinkerByUserId(int userId, EnumSet<ContributorStatus> statuses);
}
