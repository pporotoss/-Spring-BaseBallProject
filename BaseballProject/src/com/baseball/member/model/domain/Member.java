package com.baseball.member.model.domain;

public class Member {

	private int member_id;
	private String id;
	private String pwd;
	private String nickname;
	private String username;
	private String email;
	private String pwdHintQuestion;
	private String pwdHintAnswer;
	private int team_id;
	private int level_id;
	private int rank;
	
	public String getPwdHintQuestion() {
		return pwdHintQuestion;
	}
	public void setPwdHintQuestion(String pwdHintQuestion) {
		this.pwdHintQuestion = pwdHintQuestion;
	}
	public String getPwdHintAnswer() {
		return pwdHintAnswer;
	}
	public void setPwdHintAnswer(String pwdHintAnswer) {
		this.pwdHintAnswer = pwdHintAnswer;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTeam_id() {
		return team_id;
	}
	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}
	public int getLevel_id() {
		return level_id;
	}
	public void setLevel_id(int level_id) {
		this.level_id = level_id;
	}
		
}
