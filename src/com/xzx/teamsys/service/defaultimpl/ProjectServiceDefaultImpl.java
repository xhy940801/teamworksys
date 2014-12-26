package com.xzx.teamsys.service.defaultimpl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.xzx.teamsys.bean.Group;
import com.xzx.teamsys.bean.Project;
import com.xzx.teamsys.bean.ProjectUserLinker;
import com.xzx.teamsys.bean.User;
import com.xzx.teamsys.bean.UserDetail;
import com.xzx.teamsys.dao.DAOException;
import com.xzx.teamsys.dao.GroupDAO;
import com.xzx.teamsys.dao.ProjectDAO;
import com.xzx.teamsys.dao.ProjectUserLinkerDAO;
import com.xzx.teamsys.dao.TransactionManager;
import com.xzx.teamsys.dao.UserDAO;
import com.xzx.teamsys.dao.UserDetailDAO;
import com.xzx.teamsys.entity.ContributorStatus;
import com.xzx.teamsys.entity.Editable;
import com.xzx.teamsys.entity.ProjectInfo;
import com.xzx.teamsys.entity.UserInfo;
import com.xzx.teamsys.entity.UserProjectInfo;
import com.xzx.teamsys.service.OperatorException;
import com.xzx.teamsys.service.ProjectService;

public class ProjectServiceDefaultImpl extends WebServiceDefaultImpl implements ProjectService
{
	private ProjectDAO projectDAO;
	private GroupDAO groupDAO;
	private UserDAO userDAO;
	private UserDetailDAO userDetailDAO;
	private ProjectUserLinkerDAO projectUserLinkerDAO;
	private TransactionManager transactionManager;

	public ProjectServiceDefaultImpl(ProjectDAO projectDAO, GroupDAO groupDAO, UserDAO userDAO, UserDetailDAO userDetailDAO, ProjectUserLinkerDAO projectUserLinkerDAO,
			TransactionManager transactionManager)
	{
		this.projectDAO = projectDAO;
		this.groupDAO = groupDAO;
		this.userDAO = userDAO;
		this.userDetailDAO = userDetailDAO;
		this.projectUserLinkerDAO = projectUserLinkerDAO;
		this.transactionManager = transactionManager;
	}

	@Override
	public int createProject(int owner, String name, String remark)
	{
		Project project = new Project(owner, name, remark, 0);
		Group groupOwner = new Group(0, "创建者", "", Editable.FALSE);
		Group groupAdmin = new Group(0, "管理员", "", Editable.FALSE);
		ProjectUserLinker projectUserLinker = new ProjectUserLinker();
		projectUserLinker.setUserId(owner);
		projectUserLinker.setStatus(ContributorStatus.ACCEPT);

		try
		{
			transactionManager.startTrans();

			int projectId = projectDAO.save(project);
			project.setId(projectId);
			groupOwner.setProjectId(projectId);
			projectUserLinker.setProjectId(projectId);
			int groupId = groupDAO.save(groupOwner);
			groupDAO.addUserToGroup(owner, groupId);
			projectUserLinkerDAO.save(projectUserLinker);
			groupAdmin.setProjectId(projectId);
			int groupAdminId = groupDAO.save(groupAdmin);
			project.setDefaultGroupId(groupAdminId);
			projectDAO.update(project);

			transactionManager.commit();
			return projectId;
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
	public int deleteProject(int projectId)
	{
		try
		{
			transactionManager.startTrans();

			List<Group> groups = groupDAO.getGroupsByProjectId(projectId);
			for (Group group : groups)
				groupDAO.delete(group.getId());
			List<ProjectUserLinker> projectUserLinkers = projectUserLinkerDAO.getLinkerByProjectId(projectId, EnumSet.allOf(ContributorStatus.class));
			for (ProjectUserLinker projectUserLinker : projectUserLinkers)
				projectUserLinkerDAO.delete(projectUserLinker.getId());
			projectDAO.delete(projectId);

			transactionManager.commit();
			return projectId;
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
	public List<UserProjectInfo> getUserProjectInfos(int owner, ContributorStatus status)
	{
		List<UserProjectInfo> userProjectInfos = new ArrayList<UserProjectInfo>();
		try
		{
			List<ProjectUserLinker> projectUserLinkers = projectUserLinkerDAO.getLinkerByUserId(owner, status);
			for (ProjectUserLinker projectUserLinker : projectUserLinkers)
			{
				Project project = projectDAO.getProjectById(projectUserLinker.getProjectId());
				Group group = groupDAO.getGroupByUserIdAndProjectId(projectUserLinker.getUserId(), projectUserLinker.getProjectId());
				UserProjectInfo userProjectInfo;
				userProjectInfo = new UserProjectInfo(projectUserLinker.getUserId(), project, group, projectUserLinker);
				userProjectInfos.add(userProjectInfo);
			}
			return userProjectInfos;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return null;
		}
	}

	@Override
	public List<UserProjectInfo> getUserProjectInfos(int owner, EnumSet<ContributorStatus> statuses)
	{
		List<UserProjectInfo> userProjectInfos = new ArrayList<UserProjectInfo>();
		try
		{
			for (ContributorStatus status : statuses)
			{
				List<ProjectUserLinker> projectUserLinkers = projectUserLinkerDAO.getLinkerByUserId(owner, status);
				for (ProjectUserLinker projectUserLinker : projectUserLinkers)
				{
					Project project = projectDAO.getProjectById(projectUserLinker.getProjectId());
					Group group = groupDAO.getGroupByUserIdAndProjectId(projectUserLinker.getUserId(), projectUserLinker.getProjectId());
					UserProjectInfo userProjectInfo = new UserProjectInfo(projectUserLinker.getUserId(), project, group, projectUserLinker);
					userProjectInfos.add(userProjectInfo);
				}
			}
			return userProjectInfos;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return null;
		}
	}

	@Override
	public int inviteUser(String email, int projectId)
	{
		try
		{
			User user = userDAO.getUserByEmail(email);
			if(user == null)
				throw new OperatorException("用户不存在");
			int userId = user.getId();
			ProjectUserLinker projectUserLinker = projectUserLinkerDAO.getLinkerByUserIdAndProjectId(userId, projectId);
			if (projectUserLinker == null)
			{
				projectUserLinker = new ProjectUserLinker(userId, projectId, ContributorStatus.DEFAULT);
				return projectUserLinkerDAO.save(projectUserLinker);
			}
			else if (projectUserLinker.getStatus() == ContributorStatus.REFUSE)
			{
				projectUserLinker.setStatus(ContributorStatus.DEFAULT);
				projectUserLinkerDAO.update(projectUserLinker);
				return projectUserLinker.getId();
			}
			else if (projectUserLinker.getStatus() == ContributorStatus.ACCEPT)
			{
				lastError = "用户已经是项目的组员";
				return -1;
			}
			else
				return projectUserLinker.getId();
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return -1;
		}
		catch (OperatorException e)
		{
			e.printStackTrace();
			lastError = e.getMessage();
			return -1;
		}
	}

	@Override
	public int accept(int linkerId, int userId)
	{
		try
		{
			transactionManager.startTrans();

			ProjectUserLinker projectUserLinker = projectUserLinkerDAO.getLinkerById(linkerId);
			if (projectUserLinker == null)
				throw new OperatorException("用户并没收到邀请");
			if(projectUserLinker.getUserId() != userId)
				throw new OperatorException("权限不足");
			if (projectUserLinker.getStatus() != ContributorStatus.DEFAULT)
				throw new OperatorException("用户已经接受或拒绝邀请");
			projectUserLinker.setStatus(ContributorStatus.ACCEPT);
			projectUserLinkerDAO.update(projectUserLinker);
			Project project = projectDAO.getProjectById(projectUserLinker.getProjectId());
			if (project == null)
				throw new OperatorException("未知错误");
			groupDAO.addUserToGroup(projectUserLinker.getUserId(), project.getDefaultGroupId());

			transactionManager.commit();
			return projectUserLinkerDAO.save(projectUserLinker);
		}
		catch (OperatorException e)
		{
			lastError = e.getMessage();
			transactionManager.rollback();
			return -1;
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
	public int refuse(int linkerId, int userId)
	{
		try
		{
			transactionManager.startTrans();

			ProjectUserLinker projectUserLinker = projectUserLinkerDAO.getLinkerById(linkerId);
			if (projectUserLinker == null)
				throw new OperatorException("用户并没收到邀请");
			if(projectUserLinker.getUserId() != userId)
				throw new OperatorException("权限不足");
			if (projectUserLinker.getStatus() != ContributorStatus.DEFAULT)
				throw new OperatorException("用户已经接受或拒绝邀请");
			projectUserLinker.setStatus(ContributorStatus.REFUSE);
			projectUserLinkerDAO.update(projectUserLinker);

			transactionManager.commit();
			return projectUserLinkerDAO.save(projectUserLinker);
		}
		catch (OperatorException e)
		{
			lastError = e.getMessage();
			transactionManager.rollback();
			return -1;
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
	public List<UserInfo> getProjectUserInfos(int projectId)
	{
		try
		{
			List<User> users = userDAO.getContributorByProjectId(projectId, ContributorStatus.ACCEPT);
			List<UserInfo> userInfos = new ArrayList<UserInfo>();
			for(User user : users)
			{
				UserDetail userDetail = userDetailDAO.getUserDetailByUserId(user.getId());
				userInfos.add(new UserInfo(user, userDetail));
			}
			return userInfos;
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return null;
		}
	}

	@Override
	public ProjectInfo getProjectInfo(int projectId)
	{
		try
		{
			Project project = projectDAO.getProjectById(projectId);
			if(project == null)
			{
				lastError = "项目不存在";
				return null;
			}
			UserDetail userDetail = userDetailDAO.getUserDetailByUserId(project.getOwner());
			return new ProjectInfo(project, userDetail);
		}
		catch (DAOException e)
		{
			e.printStackTrace();
			lastError = "数据库操作失败";
			return null;
		}
	}
}
