package com.xzx.teamsys.service.defaultimpl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.bean.Group;
import com.xzx.teamsys.bean.Project;
import com.xzx.teamsys.bean.User;
import com.xzx.teamsys.bean.UserDetail;
import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.GroupDAO;
import com.xzx.teamsys.dao.ProjectDAO;
import com.xzx.teamsys.dao.UserDAO;
import com.xzx.teamsys.dao.UserDetailDAO;
import com.xzx.teamsys.entity.ContributorStatus;
import com.xzx.teamsys.entity.Editable;
import com.xzx.teamsys.entity.UserInfo;
import com.xzx.teamsys.service.GroupService;
import com.xzx.teamsys.service.OperatorException;

public class GroupServiceDefaultImpl extends WebServiceDefaultImpl implements
		GroupService
{
	private GroupDAO groupDAO;
	private ProjectDAO projectDAO;
	private UserDAO userDAO;
	private UserDetailDAO userDetailDAO;

	public GroupServiceDefaultImpl(GroupDAO groupDAO, ProjectDAO projectDAO,
			UserDAO userDAO, UserDetailDAO userDetailDAO)
	{
		this.groupDAO = groupDAO;
		this.projectDAO = projectDAO;
		this.userDAO = userDAO;
		this.userDetailDAO = userDetailDAO;
	}

	@Override
	public int createGroup(int projectId, String name, String remark)
	{
		Group group = new Group(projectId, name, remark, Editable.FALSE);
		try
		{
			return groupDAO.save(group);
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return -1;
		}
	}

	@Override
	public int updateGroup(int groupId, String name, String remark)
	{
		try
		{
			Group group = groupDAO.getGroupById(groupId);
			if (group == null)
				throw new OperatorException("组不存在");
			if (group.getEditable() == Editable.FALSE)
				throw new OperatorException("组不可修改");
			group.setName(name);
			group.setRemark(remark);
			groupDAO.update(group);
			return group.getId();
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
	public int deleteGroup(int groupId)
	{
		try
		{
			Group group = groupDAO.getGroupById(groupId);
			if (group == null)
				throw new OperatorException("组不存在");
			if (group.getEditable() == Editable.FALSE)
				throw new OperatorException("组不可修改");
			groupDAO.delete(group.getId());
			return 0;
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
	public int changeUserGroup(int userId, int groupId)
	{
		try
		{
			Group group = groupDAO.getGroupById(groupId);
			if (group == null)
				throw new OperatorException("组不存在");
			Project project = projectDAO.getProjectById(group.getProjectId());
			if (project == null)
				throw new OperatorException("未知错误");
			if (userId == project.getOwner())
				throw new OperatorException("创建者不能改变组别");
			Group oldGroup = groupDAO.getGroupByUserIdAndProjectId(userId,
					project.getId());
			if (oldGroup != null)
				groupDAO.deleteUserFromGroup(userId, oldGroup.getId());
			groupDAO.addUserToGroup(userId, groupId);
			return groupId;
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
	public List<UserInfo> getGroupUsers(int groupId)
	{
		try
		{
			List<UserInfo> userInfos = new ArrayList<UserInfo>();
			Group group = groupDAO.getGroupById(groupId);
			if (group == null)
				throw new OperatorException("组不存在");
			List<User> users = userDAO.getContributorByGroupId(groupId,
					EnumSet.of(ContributorStatus.ACCEPT));
			for (User user : users)
			{
				UserDetail userDetail = userDetailDAO
						.getUserDetailByUserId(user.getId());
				userInfos.add(new UserInfo(user, userDetail));
			}
			return userInfos;
		}
		catch (OperatorException e)
		{
			lastError = e.getMessage();
			return null;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return null;
		}
	}

}
