package com.squadiq.api.srvs.impl;

import java.net.URI;
import java.util.Arrays;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.squadiq.api.model.PersonUpdate;
import com.squadiq.api.srvs.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
   
	@Value("${pipedrive.baseUrl}")
	private String baseUrl;
	 
	@Value("${pipedrive.persons}")
	private String personsUrl;
	
	@Value("${app.token}")
	private String token;
	
    private static RestTemplate restTemplate = new RestTemplate();
    
    @Override
	public ResponseEntity<Object>  searchPerson(String name) {
    	String path=personsUrl+"search";
    	HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	URI builder =UriComponentsBuilder.fromHttpUrl(path).queryParam("term",name).queryParam("field", "name").queryParam("exact_match",true).queryParam("api_token",token).build().encode().toUri();
    	HttpEntity<String> entity = new HttpEntity<>(headers);
    	try {
    	ResponseEntity<String> response = restTemplate.exchange(
    	        builder, 
    	        HttpMethod.GET, 
    	        entity, 
    	        String.class);
    	return new ResponseEntity<Object>(response.getBody(), response.getStatusCode());
    	}
    	catch (Exception e) {
 		   return new ResponseEntity<Object>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    }
    
    @Override
	public ResponseEntity<Object> searchPersonById(int id) {
    	String path=personsUrl+String.valueOf(id);
    	HttpHeaders headers=new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	URI builder=UriComponentsBuilder.fromHttpUrl(path).queryParam("api_token", token).build().encode().toUri();
    	HttpEntity<String> entity=new HttpEntity<>(headers);
    	try {
    	ResponseEntity<String> response=restTemplate.exchange(builder, HttpMethod.GET,entity,String.class);
    	System.out.println(new ResponseEntity<Object>(response.getBody(), response.getStatusCode()));
    	return new ResponseEntity<Object>(response.getBody(), response.getStatusCode());
    	}
    	catch (Exception e) {
		   return new ResponseEntity<Object>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    }
    
    
    @Override
	public String updatePerson(PersonUpdate updatedInfo,String id) {
    	String path=personsUrl+id;
    	HttpHeaders headers=new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        try {
    	HttpEntity<PersonUpdate> entity = new HttpEntity<PersonUpdate>(updatedInfo,headers);
    	URI builder=UriComponentsBuilder.fromHttpUrl(path).queryParam("api_token", token).build().encode().toUri();
        
    	ResponseEntity<String> response=restTemplate.exchange(builder, HttpMethod.PUT,entity,String.class);
    	return response.getStatusCode().toString();
        }
        catch (Exception e) {
        	return e.getMessage();
		}   	
    }

}
