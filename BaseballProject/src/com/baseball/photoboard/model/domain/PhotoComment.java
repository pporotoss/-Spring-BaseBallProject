package com.baseball.photoboard.model.domain;

import java.util.Date;

public class PhotoComment {
	private int photoComment_id;
	private String content;
	private Date regdate;
	private int member_id;
	private int photoBoard_id;
	
	public int getPhotoComment_id() {
		return photoComment_id;
	}
	public void setPhotoComment_id(int photoComment_id) {
		this.photoComment_id = photoComment_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public int getPhotoBoard_id() {
		return photoBoard_id;
	}
	public void setPhotoBoard_id(int photoBoard_id) {
		this.photoBoard_id = photoBoard_id;
	}
	
}
