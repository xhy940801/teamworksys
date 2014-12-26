package com.xzx.teamsys.dao.jdbcimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.bean.ProjectUserLinker;
import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.ProjectUserLinkerDAO;
import com.xzx.teamsys.entity.ContributorStatus;

public class ProjectUserLinkerDAOJDBCImpl extends JDBCBaseDAO implements
		ProjectUserLinkerDAO
{

	@Override
	public int save(ProjectUserLinker projectUserLinker)
	{
		String sql = "INSERT INTO projects_users (user_id, project_id, status) VALUES(?,?,?)";
		PreparedStatement statement = this.preparedStatementForCreate(sql);
		try
		{
			statement.setInt(1, projectUserLinker.getUserId());
			statement.setInt(2, projectUserLinker.getProjectId());
			statement.setString(3, projectUserLinker.getStatus().toString());
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next())
				return rs.getInt(1);
			else
				throw new DAOException("unknow error");
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public int update(ProjectUserLinker projectUserLinker)
	{
		String sql = "UPDATE projects_users SET user_id=?, project_id=?, status=? WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, projectUserLinker.getUserId());
			statement.setInt(2, projectUserLinker.getProjectId());
			statement.setString(3, projectUserLinker.getStatus().toString());
			statement.setInt(4, projectUserLinker.getId());
			return statement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public int delete(int projectUserLinkerId)
	{
		String sql = "DELETE FROM projects_users WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, projectUserLinkerId);
			return statement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public ProjectUserLinker getLinkerById(int projectUserLinkerId)
	{
		String sql = "SELECT id, user_id, project_id, status FROM projects_users WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, projectUserLinkerId);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				ProjectUserLinker linker = new ProjectUserLinker();
				linker.setId(rs.getInt("id"));
				linker.setUserId(rs.getInt("user_id"));
				linker.setProjectId(rs.getInt("project_id"));
				linker.setStatus(ContributorStatus.valueOf(rs.getString("status")));
				return linker;
			}
			else
				return null;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public ProjectUserLinker getLinkerByUserIdAndProjectId(int userId,
			int projectId)
	{
		String sql = "SELECT id, user_id, project_id, status FROM projects_users WHERE user_id=? AND project_id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, userId);
			statement.setInt(2, projectId);
			ResultSet rs = statement.executeQuery();
			if(rs.next())
			{
				ProjectUserLinker linker = new ProjectUserLinker();
				linker.setId(rs.getInt("id"));
				linker.setUserId(rs.getInt("user_id"));
				linker.setProjectId(rs.getInt("project_id"));
				linker.setStatus(ContributorStatus.valueOf(rs.getString("status")));
				return linker;
			}
			else
				return null;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public List<ProjectUserLinker> getLinkerByProjectId(int projectId,
			ContributorStatus status)
	{
		return this.getLinkerByProjectId(projectId, EnumSet.of(status));
	}

	@Override
	public List<ProjectUserLinker> getLinkerByProjectId(int projectId,
			EnumSet<ContributorStatus> statuses)
	{
		String sql = "SELECT id, user_id, project_id, status FROM projects_users WHERE project_id=? AND status IN (?,?,?)";
		PreparedStatement statement = this.preparedStatement(sql);
		List<ProjectUserLinker> linkers = new ArrayList<ProjectUserLinker>();
		try
		{
			statement.setInt(1, projectId);
			int i = 2;
			for(ContributorStatus status : statuses)
				statement.setString(i++, status.toString());
			while(i < 5)
				statement.setNull(i++, Types.VARCHAR);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				ProjectUserLinker linker = new ProjectUserLinker();
				linker.setId(rs.getInt("id"));
				linker.setUserId(rs.getInt("user_id"));
				linker.setProjectId(rs.getInt("project_id"));
				linker.setStatus(ContributorStatus.valueOf(rs.getString("status")));
				linkers.add(linker);
			}
			return linkers;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public List<ProjectUserLinker> getLinkerByUserId(int userId,
			ContributorStatus status)
	{
		return this.getLinkerByUserId(userId, EnumSet.of(status));
	}

	@Override
	public List<ProjectUserLinker> getLinkerByUserId(int userId,
			EnumSet<ContributorStatus> statuses)
	{
		String sql = "SELECT id, user_id, project_id, status FROM projects_users WHERE user_id=? AND status IN (?,?,?)";
		PreparedStatement statement = this.preparedStatement(sql);
		List<ProjectUserLinker> linkers = new ArrayList<ProjectUserLinker>();
		try
		{
			statement.setInt(1, userId);
			int i = 2;
			for(ContributorStatus status : statuses)
				statement.setString(i++, status.toString());
			while(i < 5)
				statement.setNull(i++, Types.VARCHAR);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				ProjectUserLinker linker = new ProjectUserLinker();
				linker.setId(rs.getInt("id"));
				linker.setUserId(rs.getInt("user_id"));
				linker.setProjectId(rs.getInt("project_id"));
				linker.setStatus(ContributorStatus.valueOf(rs.getString("status")));
				linkers.add(linker);
			}
			return linkers;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

}
