package com.baseball.photoboard.model.domain;

import java.util.Date;

public class PhotoBoard {

	private int photoBoard_id;
	private String title;
	private String saveName;
	private int hit;
	private Date regdate;
	private int member_id;
	private String thumb1;
	private String thumb2;
	
	public int getPhotoBoard_id() {
		return photoBoard_id;
	}
	public void setPhotoBoard_id(int photoBoard_id) {
		this.photoBoard_id = photoBoard_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
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
	public String getThumb1() {
		return thumb1;
	}
	public void setThumb1(String thumb1) {
		this.thumb1 = thumb1;
	}
	public String getThumb2() {
		return thumb2;
	}
	public void setThumb2(String thumb2) {
		this.thumb2 = thumb2;
	}
	
}
