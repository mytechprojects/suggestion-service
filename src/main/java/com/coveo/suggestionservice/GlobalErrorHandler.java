package com.coveo.suggestionservice;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.coveo.suggestionservice.common.SuggestionServiceConstants;

public class GlobalErrorRestManager implements ErrorController
{
	static {
		LoggerFactory.getLogger(GlobalErrorRestManager.class);
	}
	/**
	 * Error Attributes in the Application
	 */
	private ErrorAttributes errorAttributes;

	private final static String ERROR_PATH = "/error";

	/**
	 * Controller for the Error Controller
	 * 
	 * @param errorAttributes
	 */
	public GlobalErrorRestManager(ErrorAttributes errorAttributes)
	{
		this.errorAttributes = errorAttributes;
	}

	/**
	 * Supports the HTML Error View
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH, produces = "text/html")
	public ModelAndView errorHtml(HttpServletRequest request)
	{
		return new ModelAndView("/globalerror", getErrorAttributes(request, false));

		/*
		 * Velocity seems to have been deprecated in the new spring version. In case this redirection doesn't work.. we can use direct response like below.
		 * 
		 * String customMessage = (String)getErrorAttributes(request, false).get(SuggestionServiceConstants.GLOBAL_ERRORRESPONSE_MESSAGE); Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code"); Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception"); return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>" + "<div>Exception Message: <b>%s</b></div><body></html>", statusCode, customMessage);
		 */
	}

	/**
	 * Supports other formats like JSON, XML
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ERROR_PATH)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request)
	{
		Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
		HttpStatus status = getStatus(request);
		return new ResponseEntity<Map<String, Object>>(body, status);
	}

	/**
	 * Returns the path of the error page.
	 *
	 * @return the error path
	 */
	@Override
	public String getErrorPath()
	{
		return ERROR_PATH;
	}

	private boolean getTraceParameter(HttpServletRequest request)
	{
		String parameter = request.getParameter("trace");
		if (parameter == null)
		{
			return false;
		}
		return !"false".equals(parameter.toLowerCase());
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace)
	{
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		requestAttributes.getAttributeNames(0);
		Map<String, Object> responseAttributes = this.errorAttributes.getErrorAttributes((WebRequest) request, includeStackTrace);

		// Hook method to insert / modify default error response attributes
		prepareCustomErrorAttributes(responseAttributes);
		return responseAttributes;
	}

	private void prepareCustomErrorAttributes(Map<String, Object> responseAttributes)
	{
		try
		{
			// By default, these attributes expose internal information
			responseAttributes.remove(SuggestionServiceConstants.GLOBAL_ERRORRESPONSE_EXCEPTION);
			String defaultErrorResponse = SuggestionServiceConstants.GLOBAL_ERRORRESPONSE_DEFAULTTEXT;

			responseAttributes.put(SuggestionServiceConstants.GLOBAL_ERRORRESPONSE_MESSAGE, defaultErrorResponse);
		} catch (Exception ex)
		{

		}
	}

	private HttpStatus getStatus(HttpServletRequest request)
	{
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode != null)
		{
			try
			{
				return HttpStatus.valueOf(statusCode);
			} catch (Exception ex)
			{
			}
		}
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

}
