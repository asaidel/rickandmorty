package com.rickandmortyapi.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientUtil {

	public static Map<?, ?> httpGetRequest(String uri, String param) throws URISyntaxException, IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().version(HttpClient.Version.HTTP_2)
				.uri(URI.create(uri + param))
				.headers("Accept-Enconding", "gzip, deflate").build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		String responseBody = response.body();
		//int responseStatusCode = response.statusCode();
		//System.out.println("httpGetRequest status code: " + responseStatusCode);
		
		Map<?, ?> map = new ObjectMapper().readValue(responseBody, HashMap.class);	
		
		return map;
	}
}
