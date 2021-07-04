package com.squadiq.api.srvs;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface WebhookService {

	String addWebhook(String request) throws JSONException, IOException, InterruptedException;

	ResponseEntity<Object> webhookHit(String webhookAction);

}