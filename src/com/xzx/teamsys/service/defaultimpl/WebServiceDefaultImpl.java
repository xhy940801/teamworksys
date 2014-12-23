package com.xzx.teamsys.service.defaultimpl;

import com.xzx.teamsys.service.WebService;

public class WebServiceDefaultImpl implements WebService
{
	protected String lastError = "";

	protected WebServiceDefaultImpl()
	{
		
	}

	@Override
	public String getLastError()
	{
		return lastError;
	}

}
