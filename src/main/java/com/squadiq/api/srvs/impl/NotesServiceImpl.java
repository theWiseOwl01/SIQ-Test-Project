package com.squadiq.api.srvs.impl;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.squadiq.api.srvs.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

	@Value("${pipedrive.baseUrl}")
	private String baseUrl;
	
	@Value("${app.token}")
	private String token;

	String[] req=new String[]{"content","user_Id","lead_id","deal_id","person_id","org_id","add_time","pinned_to_lead_flag","pinned_to_deal_flag","pinned_to_organization_flag","pinned_to_person_flag"};


	@Override
	public String addNotes(String payload) throws Exception {
		try {
			JSONObject obj=new JSONObject(payload);
			Map<String,String> params= new HashMap<>();	   
			for(int i=0;i<req.length;i++){
				if(obj.has(req[i])){
					params.put(req[i],obj.getString(req[i]).toString());
				}
			}
			String form = params.keySet().stream()
					.map(key -> key + "=" + URLEncoder.encode(params.get(key), StandardCharsets.UTF_8))
					.collect(Collectors.joining("&"));
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			URI builder=UriComponentsBuilder.fromHttpUrl(baseUrl+"/notes").queryParam("api_token",token).build().encode().toUri();
			HttpRequest requestt = HttpRequest.newBuilder().uri(builder)
					.headers("Content-Type", "application/x-www-form-urlencoded")
					.POST(BodyPublishers.ofString(form)).build();
			HttpClient client = HttpClient.newHttpClient();
			HttpResponse<String> response = client.send(requestt, BodyHandlers.ofString());
            return response.body();
		} catch (Exception e) {
			throw e;
		}

	}


}
