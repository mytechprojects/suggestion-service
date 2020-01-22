package com.coveo.suggestionservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.coveo.suggestionservice.common.SuggestionServiceConstants;
import com.coveo.suggestionservice.common.SuggestionServiceException;
import com.coveo.suggestionservice.component.IndexBuilder;

@Component
public class SuggestionServiceLoader implements ApplicationListener<ContextRefreshedEvent>
{
	
	public Logger logger = LoggerFactory.getLogger(IndexBuilder.class);
	
	@Autowired
	IndexBuilder geoIndexBuilder;
	
    @Value("${geodata.file:/tmp/cities500.txt}")
    private String inputFile;
    
    @Value("${spring.application.platform:local}")
    private String appMode;    
    
    // Better to inject as environment variables via deployment scripts 
    @Value("${aws.accessKey.id}")
    private String accessKeyID;  
    
    @Value("${aws.accessKey.secret}")
    private String accessKey;
    
    @Value("${aws.s3.bucket.name}")
    private String bucketName;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0)
	{
		try
		{
			if(SuggestionServiceConstants.AWSDEPLOYPLATFORM.equalsIgnoreCase(appMode)) {
				geoIndexBuilder.buildGeoIndexFromAWS(accessKeyID, accessKey, bucketName, inputFile);
			}
			else
			{
				geoIndexBuilder.buildGeoIndex(inputFile);
			}
		} catch (SuggestionServiceException e)
		{
			logger.error("Application startup error: " + e.toString());
			logger.error("Could not build index. Aborting application startup");
			System.exit(1);
		}
		
	}

}
