package com.xzx.teamsys.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigureLoader
{
	static private Properties configure = new Properties();
	
	static public Properties loadConfigure(InputStream iStream)
	{
		if(iStream == null)
			return configure;
		Properties prop = new Properties();
		try
		{
			prop.load(iStream);
			configure.putAll(prop);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return configure;
	}
	
	static public Properties getConfigure()
	{
		return configure;
	}
}
