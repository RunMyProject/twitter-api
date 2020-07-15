package com.esabatini.twitterapi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Builder
public class CheckResult {

	@NonNull private String message;
	@NonNull private Integer code;
	
	public CheckResult(String message, Integer code) {
		this.message = message;
		this.code = code;
	}
}
