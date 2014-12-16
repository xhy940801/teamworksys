package com.xzx.teamsys.dao;

import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.bean.Task;
import com.xzx.teamsys.entity.CompletionStatus;

public interface TaskDAO
{
	public int save(Task task);
	public int update(Task task);
	public Task getTaskById(int id);
	public List<Task> getTasksByProjectId(int projectId, CompletionStatus status);
	public List<Task> getTasksByProjectId(int projectId, EnumSet<CompletionStatus> statuses);
	public List<Task> getTasksByUserId(int userId, CompletionStatus status);
	public List<Task> getTasksByUserId(int userId, EnumSet<CompletionStatus> statuses);
	public List<Task> getTasksByUserIdAndProjectId(int userId, int projectId, CompletionStatus status);
	public List<Task> getTasksByUserIdAndProjectId(int userId, int projectId, EnumSet<CompletionStatus> statuses);
}
