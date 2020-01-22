package com.coveo.suggestionservice;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coveo.suggestionservice.common.SuggestionServiceConstants;
import com.coveo.suggestionservice.common.SuggestionServiceException;
import com.coveo.suggestionservice.model.SuggestionResponse;
import com.coveo.suggestionservice.model.UserRequest;
import com.coveo.suggestionservice.service.SuggestionService;
import com.coveo.suggestionservice.utils.AuthenticationUtil;
import com.coveo.suggestionservice.utils.DataValidationUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RestController
@EnableSwagger2
public class SuggestionServiceApplication {
	
	@Autowired
	private SuggestionService suggestionService;
	
	@Autowired 
	private HttpServletRequest httpRequest;
	
	public Logger logger = LoggerFactory.getLogger(SuggestionServiceApplication.class);
	
	public static void main(String[] args) {
		
		SpringApplication.run(SuggestionServiceApplication.class, args);
	}
	
	@RequestMapping(method = { RequestMethod.GET}, value = "/suggestions", produces = "application/json")
	@ApiOperation(value = "Get suggestions", httpMethod="GET", notes="Obtain suggestions based on city and latitude / longitude params")
	@ApiResponses(value = { 
		    @ApiResponse(code = 200, message = "Ok"),
		    @ApiResponse(code = 400, message = "Bad request"),
		    @ApiResponse(code = 401, message = "Unauthorized"),
		    @ApiResponse(code = 500, message = "Unexpected error") })	
	public @ResponseBody ResponseEntity<?> getSuggestions(HttpServletResponse response, 
			@ApiParam(name="X-API-Key",  value="X-API-Key", required=true)
			@RequestHeader(value="X-API-Key") String apiKey,
			@ApiParam(name="q",  value="query", required=true)
			@RequestParam(value="q") String query,
			@ApiParam(name="latitude",  value="latitude", required=false)
			@RequestParam(value="latitude", required = false) String latitude,
			@ApiParam(name="longitude",  value="longitude", required=false)
			@RequestParam(value="longitude", required = false) String longitude) throws SuggestionServiceException {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Instant start = Instant.now();
		Instant end;
		SuggestionResponse suggestionResponse =	null;
		logger.info("Received request at " + timestamp);
		try {
			if(!AuthenticationUtil.authenticate(apiKey)){
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

			if(!DataValidationUtil.isValid(query))
			{
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			UserRequest userRequest = populateRequest(query, httpRequest);
			suggestionResponse = suggestionService.getSuggestions(userRequest);
			end = Instant.now();
		}
		catch(Exception e)
		{
			end = Instant.now();
			logger.info("Time taken to service request. Response time (mS) = " + Duration.between(start, end).toMillis());
			throw new SuggestionServiceException(SuggestionServiceConstants.SERVICE_ERROR, e.getClass().getName(), e.toString());
		}
		logger.info("Time taken to service request. Response time (mS) = " + Duration.between(start, end).toMillis());
		return new ResponseEntity<>(suggestionResponse,HttpStatus.OK);
	}


	private UserRequest populateRequest(String query, HttpServletRequest httpRequest2) throws SuggestionServiceException
	{
		UserRequest request = new UserRequest(query);		
		request.setGeoAttributes(httpRequest2);
		// Any other context attribute setting go here
		
		return request;
	}
}
