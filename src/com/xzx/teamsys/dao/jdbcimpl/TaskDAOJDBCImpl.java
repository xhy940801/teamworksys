package com.xzx.teamsys.dao.jdbcimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.bean.Task;
import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.TaskDAO;
import com.xzx.teamsys.entity.CompletionStatus;

public class TaskDAOJDBCImpl extends JDBCBaseDAO implements TaskDAO
{

	@Override
	public int save(Task task)
	{
		String sql = "INSERT INTO tasks (user_id, project_id, name, remark, status, created, deadline, debriefing) VALUES(?,?,?,?,?,?,?,?)";
		String[] columnNames = { "id" };
		PreparedStatement statement = this.preparedStatement(sql, columnNames);
		try
		{
			statement.setInt(1, task.getUserId());
			statement.setInt(2, task.getProjectId());
			statement.setString(3, task.getName());
			statement.setString(4, task.getRemark());
			statement.setString(5, task.getStatus().toString());
			statement.setDate(6, task.getCreated());
			statement.setDate(7, task.getDeadline());
			statement.setString(8, task.getDebriefing());
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next())
				return rs.getInt("id");
			else
				throw new DAOException("unknow error");
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public int update(Task task)
	{
		String sql = "UPDATE  tasks SET user_id=?, project_id=?, name=?, remark=?, status=?, created=?, deadline=?, debriefing=? WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, task.getUserId());
			statement.setInt(2, task.getProjectId());
			statement.setString(3, task.getName());
			statement.setString(4, task.getRemark());
			statement.setString(5, task.getStatus().toString());
			statement.setDate(6, task.getCreated());
			statement.setDate(7, task.getDeadline());
			statement.setString(8, task.getDebriefing());
			statement.setInt(9, task.getId());
			return statement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public Task getTaskById(int taskId)
	{
		String sql = "SELECT user_id, project_id, name, remark, status, created, deadline, debriefing FROM tasks WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, taskId);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				Task task = new Task();
				task.setId(rs.getInt("id"));
				task.setUserId(rs.getInt("user_id"));
				task.setProjectId(rs.getInt("project_id"));
				task.setName(rs.getString("name"));
				task.setRemark(rs.getString("remark"));
				task.setStatus(CompletionStatus.valueOf(rs.getString("status")));
				task.setCreated(rs.getDate("created"));
				task.setDeadline(rs.getDate("deadline"));
				task.setDebriefing(rs.getString("debriefing"));
				return task;
			}
			return null;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public List<Task> getTasksByProjectId(int projectId, CompletionStatus status)
	{
		return this.getTasksByProjectId(projectId, EnumSet.of(status));
	}

	@Override
	public List<Task> getTasksByProjectId(int projectId,
			EnumSet<CompletionStatus> statuses)
	{
		String sql = "SELECT user_id, project_id, name, remark, status, created, deadline, debriefing FROM tasks WHERE project_id=? AND status IN (?,?)";
		PreparedStatement statement = this.preparedStatement(sql);
		List<Task> tasks = new ArrayList<Task>();
		try
		{
			statement.setInt(1, projectId);
			int i = 2;
			for(CompletionStatus status : statuses)
				statement.setString(i++, status.toString());
			while(i < 4)
				statement.setNull(i++, Types.VARCHAR);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				Task task = new Task();
				task.setId(rs.getInt("id"));
				task.setUserId(rs.getInt("user_id"));
				task.setProjectId(rs.getInt("project_id"));
				task.setName(rs.getString("name"));
				task.setRemark(rs.getString("remark"));
				task.setStatus(CompletionStatus.valueOf(rs.getString("status")));
				task.setCreated(rs.getDate("created"));
				task.setDeadline(rs.getDate("deadline"));
				task.setDebriefing(rs.getString("debriefing"));
				tasks.add(task);
			}
			return tasks;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public List<Task> getTasksByUserId(int userId, CompletionStatus status)
	{
		return this.getTasksByUserId(userId, EnumSet.of(status));
	}

	@Override
	public List<Task> getTasksByUserId(int userId,
			EnumSet<CompletionStatus> statuses)
	{
		String sql = "SELECT user_id, project_id, name, remark, status, created, deadline, debriefing FROM tasks WHERE user_id=? AND status IN (?,?)";
		PreparedStatement statement = this.preparedStatement(sql);
		List<Task> tasks = new ArrayList<Task>();
		try
		{
			statement.setInt(1, userId);
			int i = 2;
			for(CompletionStatus status : statuses)
				statement.setString(i++, status.toString());
			while(i < 4)
				statement.setNull(i++, Types.VARCHAR);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				Task task = new Task();
				task.setId(rs.getInt("id"));
				task.setUserId(rs.getInt("user_id"));
				task.setProjectId(rs.getInt("project_id"));
				task.setName(rs.getString("name"));
				task.setRemark(rs.getString("remark"));
				task.setStatus(CompletionStatus.valueOf(rs.getString("status")));
				task.setCreated(rs.getDate("created"));
				task.setDeadline(rs.getDate("deadline"));
				task.setDebriefing(rs.getString("debriefing"));
				tasks.add(task);
			}
			return tasks;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public List<Task> getTasksByUserIdAndProjectId(int userId, int projectId,
			CompletionStatus status)
	{
		return this.getTasksByUserIdAndProjectId(userId, projectId, EnumSet.of(status));
	}

	@Override
	public List<Task> getTasksByUserIdAndProjectId(int userId, int projectId,
			EnumSet<CompletionStatus> statuses)
	{
		String sql = "SELECT user_id, project_id, name, remark, status, created, deadline, debriefing FROM tasks WHERE user_id=? AND project_id=? status IN (?,?)";
		PreparedStatement statement = this.preparedStatement(sql);
		List<Task> tasks = new ArrayList<Task>();
		try
		{
			statement.setInt(1, userId);
			statement.setInt(2, projectId);
			int i = 3;
			for(CompletionStatus status : statuses)
				statement.setString(i++, status.toString());
			while(i < 5)
				statement.setNull(i++, Types.VARCHAR);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				Task task = new Task();
				task.setId(rs.getInt("id"));
				task.setUserId(rs.getInt("user_id"));
				task.setProjectId(rs.getInt("project_id"));
				task.setName(rs.getString("name"));
				task.setRemark(rs.getString("remark"));
				task.setStatus(CompletionStatus.valueOf(rs.getString("status")));
				task.setCreated(rs.getDate("created"));
				task.setDeadline(rs.getDate("deadline"));
				task.setDebriefing(rs.getString("debriefing"));
				tasks.add(task);
			}
			return tasks;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

}
