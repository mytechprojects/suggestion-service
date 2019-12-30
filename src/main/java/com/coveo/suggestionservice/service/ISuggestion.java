package com.coveo.suggestionservice.service;

import java.util.List;

import com.coveo.suggestionservice.model.UserRequest;

public interface ISuggestion
{
	public List getSuggestions(UserRequest userRequest);

	public List searchByCity(UserRequest userRequest);
	
	public void searchByCity1(UserRequest userRequest);

}
