package com.coveo.suggestionservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.coveo.suggestionservice.common.SuggestionServiceConstants;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class SuggestionControllerTest
{
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void whenGetRequestDoesNotHaveRequiredHeader_thenCorrectResponse() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.
				get("/suggestions").
				header(SuggestionServiceConstants.Headers.AUTH_HEADER, "test").
				param("q", "test").
				contentType(MediaType.APPLICATION_JSON)).
				andExpect(MockMvcResultMatchers.status().
				isUnauthorized());
	}
	
	@Test
	public void whenGetRequestDoesNotHaveRequiredQueryParam_thenCorrectResponse() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.
				get("/suggestions").
				header(SuggestionServiceConstants.Headers.AUTH_HEADER, SuggestionServiceConstants.VALID_API_KEY).
				contentType(MediaType.APPLICATION_JSON)).
				andExpect(MockMvcResultMatchers.status().
				isBadRequest());
	}	
}