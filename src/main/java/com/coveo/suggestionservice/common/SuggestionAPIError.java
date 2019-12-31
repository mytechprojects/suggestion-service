package com.coveo.suggestionservice.common;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import lombok.Data;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class SuggestionAPIError
{

	private HttpStatus status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	private String message;

	private String debugMessage;

	private SuggestionAPIError()
	{
		timestamp = LocalDateTime.now();
	}

	public SuggestionAPIError(HttpStatus status)
	{
		this();
		this.status = status;
	}

	public SuggestionAPIError(HttpStatus status, Throwable ex)
	{
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	public SuggestionAPIError(HttpStatus status, String message, Throwable ex)
	{
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

	public HttpStatus getStatus()
	{
		return HttpStatus.BAD_REQUEST;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getDebugMessage()
	{
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage)
	{
		this.debugMessage = debugMessage;
	}

}
