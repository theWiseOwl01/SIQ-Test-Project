package com.squadiq.api.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.squadiq.api.model.PersonUpdate;
import com.squadiq.api.srvs.NotesService;
import com.squadiq.api.srvs.PersonService;
import com.squadiq.api.srvs.WebhookService;

@RestController
public class SIQCommonControler {

	@Autowired
	private PersonService person;

	@Autowired
	private WebhookService webhookService;

	@Autowired
	private NotesService notesService;

	@GetMapping(path = "/getPersonByName",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>  getPersonDetailsName(@RequestParam String name) {
		return person.searchPerson(name);
	}

	@GetMapping(path = "/getPersonById",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPersonDetailsId(@RequestParam int id) {
		return person.searchPersonById(id);
	}

	@PostMapping(path = "/addWebhook")
	public String addWebhook(@RequestBody String payload) throws JSONException, IOException, InterruptedException {
		return webhookService.addWebhook(payload);
	}
	
	@PostMapping(path="/webhookHit",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> webhookHit(@RequestBody String webhoookPayload){
		return webhookService.webhookHit(webhoookPayload);
	}

	@PutMapping(path = "/updatePerson")
	public String updatePerson(@RequestBody PersonUpdate updatedInfo, @RequestParam String Id) {
		return person.updatePerson(updatedInfo, Id);
	}

	@PostMapping(path = "/addNotes")
	public String addNote(@RequestBody String noteData) {
		try {
			return notesService.addNotes(noteData);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
