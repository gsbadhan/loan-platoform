package com.loanplatform.pojo;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
	private Boolean status = true;
	private Map<String, String> errors;

	public Map<String, String> appendErrors(String key, String value) {
		if (errors == null) {
			errors = new HashMap<String, String>(1);
		}
		errors.put(key, value);
		return errors;
	}
}
