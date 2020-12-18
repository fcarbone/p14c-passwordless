package com.pingidentity.oidclogin.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pingidentity.oidclogin.Consts;
import com.pingidentity.oidclogin.data.PairingKey;
import com.pingidentity.oidclogin.data.Population;
import com.pingidentity.oidclogin.data.User;

@Service
public class UserService {

	@Autowired
	@Qualifier("customRestTemplate")
	RestTemplate clientRestTemplate;

	@Value("${ping-config.api-url}")
	private String apiUrl;

	public User createUser(User user, String populationId) {
		user.setPopulation(new Population(populationId));
		user.setUsername(user.getEmail());
		user.setMfaEnabled(true);

		ResponseEntity<User> newUser = clientRestTemplate
				.exchange(
						RequestEntity.post(URI.create(apiUrl + Consts.ENDPOINT_USERS))
								.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(user),
						User.class);

		return newUser.getBody();
	}

	public PairingKey createPairingKey(User user) {
		URI uri = URI.create(apiUrl + Consts.ENDPOINT_USERS + "/" + user.getId() + Consts.ENDPOINT_PAIRINKEYS);
		Map<String,List> applications = new HashMap<String, List> ();
		
		// sending an empty list of applications: all authenticator apps in the environment can be used
		applications.put("applications", new ArrayList());
		ResponseEntity<PairingKey> response = clientRestTemplate.exchange(RequestEntity.post(uri)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(applications), PairingKey.class);
		return response.getBody();

	}
	
	public PairingKey getPairingKey(User user, PairingKey key) {
		URI uri = URI.create(apiUrl + Consts.ENDPOINT_USERS + "/" + user.getId() + Consts.ENDPOINT_PAIRINKEYS + "/" + key.getId());
		
		ResponseEntity<PairingKey> response = clientRestTemplate.exchange(RequestEntity.get(uri)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build(), PairingKey.class);
		
		return response.getBody();

	}
}
