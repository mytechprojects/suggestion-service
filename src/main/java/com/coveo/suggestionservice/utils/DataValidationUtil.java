package com.coveo.suggestionservice.utils;

import org.apache.commons.lang3.StringUtils;

public class DataValidationUtil
{

	public static boolean isValid(String q)
	{
		if(StringUtils.isEmpty(q)) return false;
		
		//Any other checks such as junk characters can be added here
		
		return true;
	}

}
