package com.su.core.akka;

public enum EventActorType{
	ZERO("zero"),
	HOUR("hour");
	
	private String value;
	
	EventActorType(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	
	
}
