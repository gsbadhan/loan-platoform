package com.loanplatform.pojo;

import java.util.Map;

import lombok.Data;

@Data
public class AuthRequest {
	private String uid;
	private Map<String, Object> meta;
}
