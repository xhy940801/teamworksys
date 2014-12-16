package com.xzx.teamsys.service;

import com.xzx.teamsys.entity.Sex;
import com.xzx.teamsys.entity.UserInfo;

public interface UserService extends WebService
{
	public int register(String email, String password);
	public int changePassword(int id, String oldPwd, String newPwd);
	public int checkPassword(String email, String password);
	public int updateUserDetail(int userId, String nickname, Sex sex);
	public UserInfo getUserInfo(int id);
}
