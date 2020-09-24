package com.loanplatform.pojo;

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
}
