package com.baseball.photoboard.model.domain;

public class PhotoBoard {

	private int photoBoard_id;
	private String title;
	private String original_name;
	private String convert_name;
	private int hit;
	private String regdate;
	private int member_id;
	
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
	public String getOriginal_name() {
		return original_name;
	}
	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}
	public String getConvert_name() {
		return convert_name;
	}
	public void setConvert_name(String convert_name) {
		this.convert_name = convert_name;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
}
