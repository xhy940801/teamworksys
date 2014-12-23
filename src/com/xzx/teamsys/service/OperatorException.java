package com.xzx.teamsys.service;

public class OperatorException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1765930444877992749L;

	public OperatorException()
	{
		
	}

	public OperatorException(String message)
	{
		super(message);
	}

	public OperatorException(Throwable cause)
	{
		super(cause);
	}

	public OperatorException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public OperatorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
