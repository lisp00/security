package org.zerock.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class TokenVO {
	private String sessionId;
	private String userId;
	private String token;
	private String receive;
	private String deviceId;
}
