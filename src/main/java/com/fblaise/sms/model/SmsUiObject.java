package com.fblaise.sms.model;

public class SmsUiObject {
	private Long id;
	private Long parentId;

	public SmsUiObject() {
	}

	public SmsUiObject(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
