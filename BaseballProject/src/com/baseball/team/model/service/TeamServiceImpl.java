package com.baseball.team.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baseball.team.model.domain.Team;
import com.baseball.team.model.repository.TeamDAO;

@Service
public class TeamServiceImpl implements TeamService{

	@Autowired
	private TeamDAO teamDAO; 
	
	@Override
	public List selectList() {

		return teamDAO.selectList();
	}

	@Override
	public Team selectTeam(int team_id) {
		return teamDAO.selectTeam(team_id);
	}

	@Override
	public void updateTeam(Team team) {
		teamDAO.updateTeam(team);
	}

	@Override
	public void deleteTeam(int team_id) {
		teamDAO.deleteTeam(team_id);
	}

	@Override
	public void insertTeam(Team team) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map getTodaySchedule(String day) {
		
		Map<String, Object> scheduleMap = new HashMap<>();
		ArrayList<String[]> lft = null;
		ArrayList<String[]> rgt = null;
		Elements states = null;
		
		try {
			
			Document doc = Jsoup.connect("http://sports.news.naver.com/schedule/scoreBoard.nhn?date="+day+"&category=kbo").get();

			Element ul = doc.getElementById("todaySchedule");
			
			if(ul != null) {		// ���� ����� ������,
				Elements divs = ul.getElementsByTag("div");
				
				states = divs.select(".state");	// ������
				Elements vs_lft = divs.select(".vs_lft");	// ���� ��
				Elements vs_rgt = divs.select(".vs_rgt");	// ������ ��
				
				lft = new ArrayList<String[]>();
				rgt = new ArrayList<String[]>();
				
				for(Element element : vs_lft){
					String[] teams = element.text().trim().split(" ");
					lft.add(teams);
				}
				
				for(Element element : vs_rgt){
					String[] teams = element.text().trim().split(" ");
					rgt.add(teams);
				}
			
			}	// if
			
			scheduleMap.put("states", states);	// ������
			scheduleMap.put("lft", lft);	// ����������Ʈ
			scheduleMap.put("rgt", rgt);	// �������� ����Ʈ
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return scheduleMap;
	}

	@Override
	public List getTeamRanking() {
		
		List<String[]> rankList = new ArrayList<>();
		
		try {
			Document doc = Jsoup.connect("http://sports.news.naver.com/kbaseball/record/index.nhn").get();
			Element table = doc.getElementById("regularTeamRecordList_table");
			Elements rank = table.getElementsByTag("tr");

			for(Element content : rank){
				String[] team = content.text().split(" ");
				rankList.add(team);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rankList;
	}

	@Override
	public Map getPitcherRanking() {
		
		Map<String, ArrayList<String[]>> pitcherMap = new HashMap<>();
		
		try {
			Document doc = Jsoup.connect("http://sports.news.naver.com/kbaseball/record/index.nhn").get();

			Elements tables = doc.getElementsByClass("pitcher");	// ���� ����. 5��
			
			Iterator<Element> iter =  tables.iterator();
			
			Element e = iter.next();
			
			Elements players = e.getElementsByTag("li");
			
			ArrayList<String[]> wins = new ArrayList<String[]>();	// �ٽ�
			ArrayList<String[]> earned = new ArrayList<String[]>();	// ��å��
			ArrayList<String[]> strikes = new ArrayList<String[]>();	// Ż����
			ArrayList<String[]> saves = new ArrayList<String[]>();	// ���̺�

			int count = 0;
			
			for(Element player : players){
				count++;
				
				String[] content = new String[4];
				content[0]	=	player.getElementsByClass("ord").text();	// ����
				content[1]	=	player.getElementsByTag("strong").text();	// ����
				content[2]	=	player.getElementsByClass("team").text();	// ��
				content[3]	=	player.getElementsByTag("em").text();	// ���
				
				if(count <= 5){
					wins.add(content); // �ٽ�
				}else if(count <= 10){
					earned.add(content); // ��å��
				}else if(count <= 15){
					strikes.add(content); // Ż����
				}else{
					saves.add(content); // ���̺�
				}
			}
			
			pitcherMap.put("wins", wins);
			pitcherMap.put("earned", earned);
			pitcherMap.put("strikes", strikes);
			pitcherMap.put("saves", saves);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pitcherMap;
	}

	@Override
	public Map getHitterRanking() {
		
		Map<String, ArrayList<String[]>> hitterMap = new HashMap<>();
		try {
			Document doc = Jsoup.connect("http://sports.news.naver.com/kbaseball/record/index.nhn").get();

			Elements tables = doc.getElementsByClass("hitter");	// Ÿ�� ����. 5��
			
			Iterator<Element> iter =  tables.iterator();
			
			Element e = iter.next();
			
			Elements players = e.getElementsByTag("li");
			
			ArrayList<String[]> battings = new ArrayList<String[]>();	// Ÿ��
			ArrayList<String[]> rbis = new ArrayList<String[]>();	// Ÿ��
			ArrayList<String[]> homeruns = new ArrayList<String[]>();	// Ȩ��
			ArrayList<String[]> stealbases = new ArrayList<String[]>();	// ����

			int count = 0;
			
			for(Element player : players){
				count++;
				
				String[] content = new String[4];
				content[0]	=	player.getElementsByClass("ord").text();	// ����
				content[1]	=	player.getElementsByTag("strong").text();	// ����
				content[2]	=	player.getElementsByClass("team").text();	// ��
				content[3]	=	player.getElementsByTag("em").text();	// ���
				
				if(count <= 5){
					battings.add(content); // Ÿ��
				}else if(count <= 10){
					rbis.add(content); // Ÿ��
				}else if(count <= 15){
					homeruns.add(content); // Ȩ��
				}else{
					stealbases.add(content); // ����
				}
			}
			
			hitterMap.put("battings", battings);
			hitterMap.put("rbis", rbis);
			hitterMap.put("homeruns", homeruns);
			hitterMap.put("stealbases", stealbases);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hitterMap;
	}

}
