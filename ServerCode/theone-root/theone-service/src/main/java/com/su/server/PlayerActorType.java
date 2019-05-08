package com.su.server;

public enum PlayerActorType {
	PROCESS("process"),
	LOGOUT("logout");
	private String value;

	public String getValue() {
		return value;
	}

	private PlayerActorType(String value) {
		this.value = value;
	}
	
	
}