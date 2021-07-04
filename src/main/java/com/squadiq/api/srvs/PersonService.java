package com.squadiq.api.srvs;

import org.springframework.http.ResponseEntity;

import com.squadiq.api.model.PersonUpdate;

public interface PersonService {

	ResponseEntity<Object> searchPerson(String name);

	ResponseEntity<Object>  searchPersonById(int id);

	String updatePerson(PersonUpdate updatedInfo, String id);

}