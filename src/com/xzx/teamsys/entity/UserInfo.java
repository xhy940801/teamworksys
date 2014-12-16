package com.xzx.teamsys.entity;

import com.xzx.teamsys.bean.User;
import com.xzx.teamsys.bean.UserDetail;

public class UserInfo
{
	private int id;
	private String email;
	private String nickname;
	private Sex sex;
	
	public UserInfo()
	{
		
	}
	
	public UserInfo(User user, UserDetail userDetail)
	{
		this.id = user.getId();
		this.email = user.getEmail();
		this.nickname = userDetail.getNickname();
		this.sex = userDetail.getSex();
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getNickname()
	{
		return nickname;
	}
	
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	
	public Sex getSex()
	{
		return sex;
	}
	
	public void setSex(Sex sex)
	{
		this.sex = sex;
	}
	
}
