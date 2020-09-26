package com.loanplatform.common;

public enum Messages {

	NOT_SUPPORTED("Functionality not supported"), UNAUTHORIZED("Not authorized"), NOT_FOUND("Requested data not found"),
	SERVER_ERROR("Getting error from server");

	private final String value;

	Messages(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
