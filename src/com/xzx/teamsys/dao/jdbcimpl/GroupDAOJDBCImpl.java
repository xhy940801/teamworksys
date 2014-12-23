package com.xzx.teamsys.dao.jdbcimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xzx.teamsys.bean.Group;
import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.GroupDAO;
import com.xzx.teamsys.entity.Editable;

public class GroupDAOJDBCImpl extends JDBCBaseDAO implements GroupDAO
{

	@Override
	public int save(Group group)
	{
		String sql = "INSERT INTO groups (project_id, name, remark, editable) VALUES(?,?,?,?)";
		String[] columnNames = { "id" };
		PreparedStatement statement = this.preparedStatement(sql, columnNames);
		try
		{
			statement.setInt(1, group.getProjectId());
			statement.setString(2, group.getName());
			statement.setString(3, group.getRemark());
			statement.setString(4, group.getEditable().toString());
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
	public int update(Group group)
	{
		String sql = "UPDATE groups SET project_id=?, name=?, remark=?, editable=? WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, group.getProjectId());
			statement.setString(2, group.getName());
			statement.setString(3, group.getRemark());
			statement.setString(4, group.getEditable().toString());
			statement.setInt(5, group.getId());
			return statement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public int delete(int groupId)
	{
		String sql = "DELETE FROM groups WHERE id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, groupId);
			return statement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public int addUserToGroup(int userId, int groupId)
	{
		String sql = "INSERT INTO groups_users (user_id, group_id) VALUES(?,?)";
		String[] columnNames = { "id" };
		PreparedStatement statement = this.preparedStatement(sql, columnNames);
		try
		{
			statement.setInt(1, userId);
			statement.setInt(2, groupId);
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
	public int deleteUserFromGroup(int userId, int groupId)
	{
		String sql = "DELETE FROM groups_users WHERE user_id=? AND group_id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, userId);
			statement.setInt(2, groupId);
			return statement.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public Group getGroupById(int id)
	{
		String sql = "SELECT id, project_id, name, remark, editable FROM groups where id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next())
			{
				Group group = new Group();
				group.setId(rs.getInt("id"));
				group.setProjectId(rs.getInt("project_id"));
				group.setName(rs.getString("name"));
				group.setRemark(rs.getString("remark"));
				group.setEditable(Editable.valueOf(rs.getString("editable")));
				return group;
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
	public Group getGroupByUserIdAndProjectId(int userId, int projectId)
	{
		String sql = "SELECT id, project_id, name, remark, editable FROM groups where user_id=? AND project_id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		try
		{
			statement.setInt(1, userId);
			statement.setInt(2, projectId);
			ResultSet rs = statement.executeQuery();
			if (rs.next())
			{
				Group group = new Group();
				group.setId(rs.getInt("id"));
				group.setProjectId(rs.getInt("project_id"));
				group.setName(rs.getString("name"));
				group.setRemark(rs.getString("remark"));
				group.setEditable(Editable.valueOf(rs.getString("editable")));
				return group;
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
	public List<Group> getGroupsByProjectId(int projectId)
	{
		String sql = "SELECT id, project_id, name, remark, editable FROM groups where project_id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		List<Group> groups = new ArrayList<Group>();
		try
		{
			statement.setInt(1, projectId);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				Group group = new Group();
				group.setId(rs.getInt("id"));
				group.setProjectId(rs.getInt("project_id"));
				group.setName(rs.getString("name"));
				group.setRemark(rs.getString("remark"));
				group.setEditable(Editable.valueOf(rs.getString("editable")));
				groups.add(group);
			}
			return groups;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

	@Override
	public List<Group> getGroupsByUserId(int userId)
	{
		String sql = "SELECT groups.id, groups.project_id, groups.name, groups.remark, groups.editable FROM groups LEFT JOIN groups_users ON groups.id = groups_users.group_id WHERE groups_users.user_id=?";
		PreparedStatement statement = this.preparedStatement(sql);
		List<Group> groups = new ArrayList<Group>();
		try
		{
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
			{
				Group group = new Group();
				group.setId(rs.getInt("id"));
				group.setProjectId(rs.getInt("project_id"));
				group.setName(rs.getString("name"));
				group.setRemark(rs.getString("remark"));
				group.setEditable(Editable.valueOf(rs.getString("editable")));
				groups.add(group);
			}
			return groups;
		}
		catch (SQLException e)
		{
			throw new DAOException("excute sql exception", e);
		}
	}

}
