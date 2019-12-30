package com.coveo.suggestionservice.common;

public class SuggestionServiceException extends Exception
{

	private static final long serialVersionUID = 1L;

	private String errorCode;

	private String exceptionDetail;

	public SuggestionServiceException(String errorCode, String exceptionType, String message) {
		super(message);
		this.exceptionDetail = exceptionType + " : " + message;
		this.errorCode = errorCode;
	}

	/**
	 * Typically we would want to have a format that matches the application format used by systems like SPLUNK This will help during debugging in deployed environments
	 */
	public String toString()
	{
		return String.format("Error: %s; Detail: %s", this.errorCode, this.exceptionDetail);
	}

}
