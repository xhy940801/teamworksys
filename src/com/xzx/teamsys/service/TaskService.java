package com.xzx.teamsys.service;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.bean.Task;
import com.xzx.teamsys.entity.CompletionStatus;

public interface TaskService extends WebService
{
	public int createTask(int projectId, int userId, String name, String remark, Date deadline);
	public int completeTask(int taskId, int userId, String debriefing);
	public int resetTask(int taskId, int userId);
	public List<Task> getUserTasks(int userId, CompletionStatus status);
	public List<Task> getUserTasks(int userId, EnumSet<CompletionStatus> statuses);
	public List<Task> getUserProjectTasks(int userId, int projectId, CompletionStatus status);
	public List<Task> getUserProjectTasks(int userId, int projectId, EnumSet<CompletionStatus> statuses);
}