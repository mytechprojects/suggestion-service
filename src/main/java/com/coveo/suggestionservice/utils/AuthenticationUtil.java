package com.coveo.suggestionservice.utils;

import org.apache.commons.lang3.StringUtils;

import com.coveo.suggestionservice.common.SuggestionServiceConstants;

public class AuthenticationUtil
{
	/**
	 * 
	 * This will plug-in to the organization level auth library
	 * 
	 * @param apiKey
	 * @return
	 */
	public static boolean authenticate(String apiKey)
	{
		if(StringUtils.isEmpty(apiKey)) {
			return false;
		}
		
		return SuggestionServiceConstants.VALID_API_KEY.equals(apiKey);

	}

}
