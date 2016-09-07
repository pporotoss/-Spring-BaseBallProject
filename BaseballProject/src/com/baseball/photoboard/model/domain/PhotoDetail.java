package com.baseball.photoboard.model.domain;

public class PhotoDetail extends PhotoBoard{
	
	private String nickname;
	private String levelname;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	
}
