package com.highfd.model;

public class EventState {
	
	private boolean eventFlag;
	private Long evnetTime;
	
	public EventState() {
		super();
	}

	public EventState(boolean eventFlag, Long evnetTime) {
		super();
		this.eventFlag = eventFlag;
		this.evnetTime = evnetTime;
	}

	public boolean isEventFlag() {
		return eventFlag;
	}
	public void setEventFlag(boolean eventFlag) {
		this.eventFlag = eventFlag;
	}
	public Long getEvnetTime() {
		return evnetTime;
	}
	public void setEvnetTime(Long evnetTime) {
		this.evnetTime = evnetTime;
	}

}
