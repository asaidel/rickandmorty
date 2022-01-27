package com.rickandmortyapi.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rickandmortyapi.service.RickAndMortyService;

@RestController
public class RickAndMortyController {

	@Autowired 
	RickAndMortyService rickAndMortyService;	
		
	@GetMapping("/rickandmorty/characterorigin/{id}")
	public ResponseEntity<Map<?, ?>> getCharacterById(@PathVariable String id) {
		try {
			return new ResponseEntity<Map<?, ?>>(rickAndMortyService.findById(id), HttpStatus.OK);
		} catch (URISyntaxException | IOException | InterruptedException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
