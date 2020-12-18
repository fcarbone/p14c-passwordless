package com.pingidentity.oidclogin.data;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PairingKey implements Serializable {

	@JsonProperty("id")
	private String id;
	@JsonProperty("code")
	private String code;
	@JsonProperty("status")
	private String status;
	private final static long serialVersionUID = -6221578307012207076L;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public PairingKey() {
	}

	/**
	 *
	 * @param code
	 * @param id
	 * @param status
	 */
	public PairingKey(String id, String code, String status) {
		super();
		this.id = id;
		this.code = code;
		this.status = status;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}
	

}