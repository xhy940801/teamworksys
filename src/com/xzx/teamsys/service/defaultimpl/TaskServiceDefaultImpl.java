package com.xzx.teamsys.service.defaultimpl;

import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import com.xzx.teamsys.bean.Task;
import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.TaskDAO;
import com.xzx.teamsys.entity.CompletionStatus;
import com.xzx.teamsys.service.OperatorException;
import com.xzx.teamsys.service.TaskService;

public class TaskServiceDefaultImpl extends WebServiceDefaultImpl implements
		TaskService
{
	private TaskDAO taskDAO;

	@Override
	public int createTask(int projectId, int userId, String name,
			String remark, Date deadline)
	{
		Task task = new Task(userId, projectId, name, remark, CompletionStatus.UNFINISHED, new Date(), deadline, null);
		try
		{
			return taskDAO.save(task);
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return -1;
		}
	}

	@Override
	public int completeTask(int taskId, int userId, String debriefing)
	{
		try
		{
			Task task = taskDAO.getTaskById(taskId);
			if(task == null)
				throw new OperatorException("任务不存在");
			if(task.getUserId() != userId)
				throw new OperatorException("权限不足");
			if(task.getStatus() == CompletionStatus.FINISHED)
				throw new OperatorException("任务已经完成");
			task.setStatus(CompletionStatus.FINISHED);
			taskDAO.update(task);
			return task.getId();
		}
		catch (OperatorException e)
		{
			lastError = e.getMessage();
			return -1;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return -1;
		}
	}

	@Override
	public int resetTask(int taskId, int userId)
	{
		try
		{
			Task task = taskDAO.getTaskById(taskId);
			if(task == null)
				throw new OperatorException("任务不存在");
			if(task.getUserId() != userId)
				throw new OperatorException("权限不足");
			task.setStatus(CompletionStatus.UNFINISHED);
			taskDAO.update(task);
			return task.getId();
		}
		catch (OperatorException e)
		{
			lastError = e.getMessage();
			return -1;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return -1;
		}
	}

	@Override
	public List<Task> getUserTasks(int userId, CompletionStatus status)
	{
		return this.getUserTasks(userId, EnumSet.of(status));
	}

	@Override
	public List<Task> getUserTasks(int userId, EnumSet<CompletionStatus> statuses)
	{
		try
		{
			EnumSet<CompletionStatus> sqlStatuses = EnumSet.copyOf(statuses);
			if(statuses.contains(CompletionStatus.OVERDUE))
			{
				sqlStatuses.remove(CompletionStatus.OVERDUE);
				sqlStatuses.add(CompletionStatus.FINISHED);
			}
			List<Task> tasks = taskDAO.getTasksByUserId(userId, sqlStatuses);
			if(!statuses.contains(CompletionStatus.OVERDUE))
			{
				Iterator<Task> it = tasks.iterator();
				Date now = new Date();
				while(it.hasNext())
				{
					Task task = it.next();
					if(task.getDeadline().before(now))
						it.remove();
				}
			}
			if(!statuses.contains(CompletionStatus.FINISHED))
			{
				Iterator<Task> it = tasks.iterator();
				Date now = new Date();
				while(it.hasNext())
				{
					Task task = it.next();
					if(task.getDeadline().after(now))
						it.remove();
				}
			}
			return tasks;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return null;
		}
	}

	@Override
	public List<Task> getUserProjectTasks(int userId, int projectId,
			CompletionStatus status)
	{
		return this.getUserProjectTasks(userId, projectId, EnumSet.of(status));
	}

	@Override
	public List<Task> getUserProjectTasks(int userId, int projectId,
			EnumSet<CompletionStatus> statuses)
	{
		try
		{
			EnumSet<CompletionStatus> sqlStatuses = EnumSet.copyOf(statuses);
			if(statuses.contains(CompletionStatus.OVERDUE))
			{
				sqlStatuses.remove(CompletionStatus.OVERDUE);
				sqlStatuses.add(CompletionStatus.FINISHED);
			}
			List<Task> tasks = taskDAO.getTasksByUserIdAndProjectId(userId, projectId, sqlStatuses);
			if(!statuses.contains(CompletionStatus.OVERDUE))
			{
				Iterator<Task> it = tasks.iterator();
				Date now = new Date();
				while(it.hasNext())
				{
					Task task = it.next();
					if(task.getDeadline().before(now))
						it.remove();
				}
			}
			if(!statuses.contains(CompletionStatus.FINISHED))
			{
				Iterator<Task> it = tasks.iterator();
				Date now = new Date();
				while(it.hasNext())
				{
					Task task = it.next();
					if(task.getDeadline().after(now))
						it.remove();
				}
			}
			return tasks;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return null;
		}
	}

}
