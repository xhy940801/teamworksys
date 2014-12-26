package com.xzx.teamsys.dao.jdbcimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xzx.teamsys.bean.Project;
import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.ProjectDAO;

public class ProjectDAOJDBCImpl extends JDBCBaseDAO implements ProjectDAO
{

	@Override
	public int save(Project project)
	{
		String sql = "INSERT INTO projects (name, owner, remark, default_group_id) values(?,?,?,?)";
		PreparedStatement statement = this.preparedStatementForCreate(sql);
		try
		{
			statement.setString(1, project.getName());
			statement.setInt(2, project.getOwner());
			statement.setString(3, project.getRemark());
			statement.setInt(4, project.getDefaultGroupId());
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
	public int update(Project project)
	{
		String sql = "UPDATE projects set name=?, owner=?, remark=?, default_group_id=? WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setString(1, project.getName());
			statement.setInt(2, project.getOwner());
			statement.setString(3, project.getRemark());
			statement.setInt(4, project.getDefaultGroupId());
			statement.setInt(5, project.getId());
			return statement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public int delete(int id)
	{
		String sql = "DELETE FROM projects WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, id);
			return statement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public Project getProjectById(int id)
	{
		String sql = "SELECT id, name, owner, remark, default_group_id FROM projects WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			Project project = null;
			if(rs.next())
			{
				project = new Project();
				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
				project.setOwner(rs.getInt("owner"));
				project.setRemark(rs.getString("remark"));
				project.setDefaultGroupId(rs.getInt("default_group_id"));
			}
			return project;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public List<Project> getProjectsByOwner(int owner)
	{
		String sql = "SELECT id, name=, owner, remark, default_group_id WHERE owner=?";
		PreparedStatement statement = this.preparedStatement(sql);
		List<Project> projects = new ArrayList<Project>();
		try
		{
			statement.setInt(1, owner);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
				project.setOwner(rs.getInt("owner"));
				project.setRemark(rs.getString("remark"));
				project.setDefaultGroupId(rs.getInt("default_group_id"));
				projects.add(project);
			}
			return projects;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

}
