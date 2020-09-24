package com.loanplatform.common;

public enum Messages {

	AUTH_SERVICE_DOWN("auth service not working"), NOT_SUPPORTED("Functionality not supported");

	private final String value;

	Messages(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
