package com.rickandmortyapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import com.networknt.schema.SpecVersion;
import com.rickandmortyapi.controller.RickAndMortyController;
import com.rickandmortyapi.util.HttpClientUtil;

@SpringBootTest
class RickandmortyapiApplicationTests {

	@Autowired
	RickAndMortyController rymcontroller;
	
	@Value( "${address}" )
	private String uri;
		
	@Test
	void ensureThatAPICallReturnsNotNull() throws Exception {	
		Map<?, ?> req = HttpClientUtil.httpGetRequest(uri,"1");
		
		assertNotNull(req);		
	}
	
	@Test
	void ensureThatJSONSchemaIsOK() throws Exception {
		 JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
		 InputStream is = Thread.currentThread().getContextClassLoader()
		            .getResourceAsStream("static/schema.json");
		
		factory.getSchema(is);
		assertNotNull(is);		
	}	
	
	@Test
	void ensureThatUserAPIResponseIsValidUponSchema() throws Exception {
		JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
		InputStream is = Thread.currentThread().getContextClassLoader()
		            .getResourceAsStream("static/schema.json");		

	    JsonSchema schema = factory.getSchema(is);
	  
	    var character = rymcontroller.getCharacterById("1");
	    
	    ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.valueToTree(character);
        
		assertNotNull(node);
		
        Set<ValidationMessage> errors = schema.validate(node.get("body"));
        
        //errors.forEach(e -> System.out.println(e) );
        
		assertEquals(0, errors.size());		
	}	

}
