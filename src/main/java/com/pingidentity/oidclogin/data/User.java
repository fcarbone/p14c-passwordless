package com.pingidentity.oidclogin.data;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	private String id;
	private String username;
	@Email(message = "Email should be valid")
	private String email;
	private Name name;
	private Population population;
	private boolean mfaEnabled;
	
	
	public User() {
		super();
		name = new Name ();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Population getPopulation() {
		return population;
	}

	public void setPopulation(Population population) {
		this.population = population;
	}

	public boolean isMfaEnabled() {
		return mfaEnabled;
	}

	public void setMfaEnabled(boolean mfaEnabled) {
		this.mfaEnabled = mfaEnabled;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
	
	public class Name {
		private String given;
		private String family;
		
		
		public Name() {
			super();
		}

		public Name(String given, String family) {
			super();
			this.given = given;
			this.family = family;
		}

		public String getGiven() {
			return given;
		}

		public void setGiven(String given) {
			this.given = given;
		}

		public String getFamily() {
			return family;
		}

		public void setFamily(String family) {
			this.family = family;
		}

	}

}
