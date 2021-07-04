package com.squadiq.api.srvs.impl;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.xml.ws.Response;

import com.squadiq.api.srvs.PersonService;
import com.squadiq.api.srvs.WebhookService;

@Service
@RestController
public class WebhookServiceImpl implements WebhookService {

	@Value("${pipedrive.createWebhook}")
	private String createWebhookUrl;

	@Value("${app.token}")
	private String token;

	@Value("${SIQStop}")
	private String siqS;

	@Autowired
	private PersonService personService;

	String[] req = new String[] { "subscription_url", "event_action", "event_object", "user_id", "http_auth_user",
			"http_auth_password" };

	private static RestTemplate restTemplate = new RestTemplate();

	@Override
	public String addWebhook(@RequestBody String request) throws JSONException, IOException, InterruptedException {
		JSONObject body = new JSONObject(request);
		Map<String, String> params = new HashMap<>();
		for (int i = 0; i < req.length; i++) {
			if (body.has(req[i])) {
				params.put(req[i], body.getString(req[i]).toString());
			}
		}
		String form = params.keySet().stream()
				.map(key -> key + "=" + URLEncoder.encode(params.get(key), StandardCharsets.UTF_8))
				.collect(Collectors.joining("&"));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		URI builder = UriComponentsBuilder.fromHttpUrl(createWebhookUrl).queryParam("api_token", token).build().encode()
				.toUri();
		HttpRequest requestt = HttpRequest.newBuilder().uri(builder)
				.headers("Content-Type", "application/x-www-form-urlencoded").POST(BodyPublishers.ofString(form))
				.build();
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<?> response = client.send(requestt, BodyHandlers.ofString());
		return (response.statusCode() + response.body().toString());
	}

	@Override
	public ResponseEntity<Object> webhookHit(String webhookAction) {
		try {
			JSONObject obj = new JSONObject(webhookAction);
			String meta = obj.getString("meta");
			JSONObject dataObj = new JSONObject(meta);
			String action = dataObj.getString("action");
			int personId = dataObj.getInt("id");
			ResponseEntity<Object> response = personService.searchPersonById(personId);
			JSONObject personObj = new JSONObject(response.getBody().toString());
			JSONObject personObj2 = new JSONObject(personObj.getString("data"));
			String siqFlag = null;
			if (personObj2.getString(siqS).equalsIgnoreCase("19")) {
				siqFlag = "False";
			} else {
				siqFlag = "True";
			}
			String name = personObj2.getString("name");
			String output = "Siq=" + siqFlag + "& Action=" + action + ", For person=" + new JSONObject(personService.searchPerson(name).getBody().toString()).getString("data");
			return new ResponseEntity<Object>(output, response.getStatusCode());
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
