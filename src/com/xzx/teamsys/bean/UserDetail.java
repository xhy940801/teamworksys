package com.xzx.teamsys.bean;

import com.xzx.teamsys.entity.Sex;

public class UserDetail
{
	private int id;
	private int userId;
	private String nickname;
	private Sex sex;
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getUserId()
	{
		return userId;
	}
	
	public void setUserId(int userId)
	{
		this.userId = userId;
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
