package com.xzx.teamsys.service;

import java.util.List;

import com.xzx.teamsys.entity.UserInfo;

public interface GroupService extends WebService
{
	public int createGroup(int projectId, String name, String remark);
	public int updateGroup(int groupId, String name, String remark);
	public int deleteGroup(int groupId);
	public int changeUserGroup(int userId, int groupId);
	public List<UserInfo> getGroupUsers(int groupId);
}
