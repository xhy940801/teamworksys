package com.xzx.teamsys.service.defaultimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import com.xzx.teamsys.bean.Task;
import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.TaskDAO;
import com.xzx.teamsys.dao.TransactionManager;
import com.xzx.teamsys.entity.CompletionStatus;
import com.xzx.teamsys.service.OperatorException;
import com.xzx.teamsys.service.TaskService;

public class TaskServiceDefaultImpl extends WebServiceDefaultImpl implements
		TaskService
{
	private TaskDAO taskDAO;
	private TransactionManager transactionManager;
	
	public TaskServiceDefaultImpl(TaskDAO taskDAO, TransactionManager transactionManager)
	{
		this.taskDAO = taskDAO;
		this.transactionManager = transactionManager;
	}

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
	public int createTask(int projectId, Integer[] userIds, String name, String remark, Date deadline)
	{
		Task[] tasks = new Task[userIds.length];
		for(int i = 0; i < tasks.length; ++i)
			tasks[i] = new Task(userIds[i], projectId, name, remark, CompletionStatus.UNFINISHED, new Date(), deadline, null);
		try
		{
			transactionManager.startTrans();
			
			for(Task task : tasks)
				taskDAO.save(task);
			
			transactionManager.commit();
			
			return 0;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			transactionManager.rollback();
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
	public List<com.xzx.teamsys.entity.Task> getUserTasks(int userId, CompletionStatus status)
	{
		return this.getUserTasks(userId, EnumSet.of(status));
	}

	@Override
	public List<com.xzx.teamsys.entity.Task> getUserTasks(int userId, EnumSet<CompletionStatus> statuses)
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
			return this.transferTasks(tasks);
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return null;
		}
	}

	@Override
	public List<com.xzx.teamsys.entity.Task> getUserProjectTasks(int userId, int projectId,
			CompletionStatus status)
	{
		return this.getUserProjectTasks(userId, projectId, EnumSet.of(status));
	}

	@Override
	public List<com.xzx.teamsys.entity.Task> getUserProjectTasks(int userId, int projectId,
			EnumSet<CompletionStatus> statuses)
	{
		try
		{
			EnumSet<CompletionStatus> sqlStatuses = EnumSet.copyOf(statuses);
			if(statuses.contains(CompletionStatus.OVERDUE))
			{
				sqlStatuses.remove(CompletionStatus.OVERDUE);
				sqlStatuses.add(CompletionStatus.UNFINISHED);
			}
			List<Task> tasks = taskDAO.getTasksByUserIdAndProjectId(userId, projectId, sqlStatuses);
			if(!statuses.contains(CompletionStatus.OVERDUE))
			{
				Iterator<Task> it = tasks.iterator();
				Date now = new Date();
				while(it.hasNext())
				{
					Task task = it.next();
					if(task.getStatus() == CompletionStatus.UNFINISHED && task.getDeadline().before(now))
						it.remove();
				}
			}
			if(!statuses.contains(CompletionStatus.UNFINISHED))
			{
				Iterator<Task> it = tasks.iterator();
				Date now = new Date();
				while(it.hasNext())
				{
					Task task = it.next();
					if(task.getStatus() == CompletionStatus.UNFINISHED && task.getDeadline().after(now))
						it.remove();
				}
			}
			return this.transferTasks(tasks);
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return null;
		}
	}
	
	private List<com.xzx.teamsys.entity.Task> transferTasks(List<Task> tasks)
	{
		List<com.xzx.teamsys.entity.Task> result = new ArrayList<com.xzx.teamsys.entity.Task>();
		for(Task task : tasks)
			result.add(new com.xzx.teamsys.entity.Task(task));
		return result;
	}

}
