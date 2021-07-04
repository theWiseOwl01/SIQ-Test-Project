package com.squadiq.api.model;

import java.util.List;


public class PersonUpdate {
	
	private String name;
	private int owner_id;
	private int org_id;
	List<String> phone;
	List<String> email;
	private String visible_to;
	public String getVisible_to() {
		return visible_to;
	}
	public void setVisible_to(String visible_to) {
		this.visible_to = visible_to;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}
	public int getOrg_id() {
		return org_id;
	}
	public void setOrg_id(int org_id) {
		this.org_id = org_id;
	}
	public List<String> getPhone() {
		return phone;
	}
	public void setPhone(List<String> phone) {
		this.phone = phone;
	}
	public List<String> getEmail() {
		return email;
	}
	public void setEmail(List<String> email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "PersonUpdate [name=" + name + ", owner_id=" + owner_id + ", org_id=" + org_id + ", phone=" + phone
				+ ", email=" + email + "]";
	}
	
	


}
