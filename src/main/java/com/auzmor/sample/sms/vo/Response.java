package com.auzmor.sample.sms.vo;

public final class Response {

	private String message;

	private String error;

	public Response(String message, String error) {
		this.message = message;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public String getError() {
		return error;
	}

}
