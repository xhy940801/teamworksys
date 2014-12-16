package com.xzx.teamsys.dao;

import java.util.List;

import com.xzx.teamsys.bean.Group;

public interface GroupDAO
{
	public int save(Group group);
	public int update(Group group);
	public int delete(int id);
	public int putUserInGroup(int userId, int groupId);
	public Group getGroupById(int id);
	public Group getGroupByUserIdAndProjectId(int userId, int projectId);
	public List<Group> getGroupsByProjectId(int projectId);
	public List<Group> getGroupsByUserId(int projectId);
}
