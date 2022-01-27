package com.rickandmortyapi.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rickandmortyapi.util.HttpClientUtil;

@Service
public class RickAndMortyService {
	
	@Value( "${address}" )
	private String uri;
		
	public Map<?, ?> findById(String id) throws URISyntaxException, IOException, InterruptedException {
		
		Map<?, ?> ch = HttpClientUtil.httpGetRequest(this.uri, id);
				
		var j = new LinkedHashMap<>();
		
		j.put("id", ch.get("id"));
		j.put("name", ch.get("name"));
		j.put("status", ch.get("status"));	
		j.put("species", ch.get("species"));
		j.put("type", ch.get("type"));
		j.put("episode_count", getCount(ch));
		
		var origin = (Map<?, ?>) ch.get("origin");
		String url = (String) origin.get("url");
		var location = findLocationByUrl(url);
		
		j.put("origin", location);
		
		return j;
	}

	private static Integer getCount(Map<?, ?> ch) {
		var a = (List<?>) ch.get("episode");	
		return a.size();
	}

	private static Map<?, ?> findLocationByUrl(String url) throws URISyntaxException, IOException, InterruptedException {
		Map<?, ?> j = findByUrl(url);
		
		var location = new LinkedHashMap<>();		
		location.put("name", j.get("name"));
		location.put("url", j.get("url"));
		location.put("dimension", j.get("dimension"));
		location.put("residents", j.get("residents"));
				
		return location;
	}	

	private static Map<?, ?> findByUrl(String url) throws URISyntaxException, IOException, InterruptedException {
		Map<?, ?> map = HttpClientUtil.httpGetRequest(url,"");
		return map;
	}
}
