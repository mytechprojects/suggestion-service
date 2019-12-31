package com.coveo.suggestionservice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coveo.suggestionservice.common.SuggestionAPIError;
import com.coveo.suggestionservice.common.SuggestionServiceException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the SuggestionAPIError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new SuggestionAPIError(BAD_REQUEST, error, ex));
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
    	SuggestionAPIError apiError = new SuggestionAPIError(BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }
    
    @ExceptionHandler(SuggestionServiceException.class)
    protected ResponseEntity<Object> handleSuggestionException(SuggestionServiceException ex,
                                                                      WebRequest request) {
    	SuggestionAPIError apiError = new SuggestionAPIError(BAD_REQUEST);
    	apiError.setMessage("Suggestion Service encountered exception ");
    	apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

	private ResponseEntity<Object> buildResponseEntity(SuggestionAPIError suggestionAPIError)
	{
		return new ResponseEntity<>(suggestionAPIError, suggestionAPIError.getStatus());
	}
}