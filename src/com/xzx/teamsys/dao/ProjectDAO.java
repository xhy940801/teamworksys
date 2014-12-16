package com.xzx.teamsys.dao;

import java.util.List;

import com.xzx.teamsys.bean.Project;

public interface ProjectDAO
{
	public int save(Project project);
	public int update(Project project);
	public int deleteProject(int id);
	public Project getProjectById(int id);
	public List<Project> getProjectsByOwner(int owner);
}
