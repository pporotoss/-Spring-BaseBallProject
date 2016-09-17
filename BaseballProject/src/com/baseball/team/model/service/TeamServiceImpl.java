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
			
			if(ul != null) {		// 오늘 결과가 있으면,
				Elements divs = ul.getElementsByTag("div");
				
				states = divs.select(".state");	// 경기상태
				Elements vs_lft = divs.select(".vs_lft");	// 왼쪽 팀
				Elements vs_rgt = divs.select(".vs_rgt");	// 오른쪽 팀
				
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
			
			scheduleMap.put("states", states);	// 경기상태
			scheduleMap.put("lft", lft);	// 왼쪽팀리스트
			scheduleMap.put("rgt", rgt);	// 오른쪽팀 리스트
			
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

			Elements tables = doc.getElementsByClass("pitcher");	// 투수 순위. 5명씩
			
			Iterator<Element> iter =  tables.iterator();
			
			Element e = iter.next();
			
			Elements players = e.getElementsByTag("li");
			
			ArrayList<String[]> wins = new ArrayList<String[]>();	// 다승
			ArrayList<String[]> earned = new ArrayList<String[]>();	// 자책점
			ArrayList<String[]> strikes = new ArrayList<String[]>();	// 탈삼진
			ArrayList<String[]> saves = new ArrayList<String[]>();	// 세이브

			int count = 0;
			
			for(Element player : players){
				count++;
				
				String[] content = new String[4];
				content[0]	=	player.getElementsByClass("ord").text();	// 순위
				content[1]	=	player.getElementsByTag("strong").text();	// 선수
				content[2]	=	player.getElementsByClass("team").text();	// 팀
				content[3]	=	player.getElementsByTag("em").text();	// 기록
				
				if(count <= 5){
					wins.add(content); // 다승
				}else if(count <= 10){
					earned.add(content); // 자책점
				}else if(count <= 15){
					strikes.add(content); // 탈삼진
				}else{
					saves.add(content); // 세이브
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

			Elements tables = doc.getElementsByClass("hitter");	// 타자 순위. 5명씩
			
			Iterator<Element> iter =  tables.iterator();
			
			Element e = iter.next();
			
			Elements players = e.getElementsByTag("li");
			
			ArrayList<String[]> battings = new ArrayList<String[]>();	// 타율
			ArrayList<String[]> rbis = new ArrayList<String[]>();	// 타점
			ArrayList<String[]> homeruns = new ArrayList<String[]>();	// 홈런
			ArrayList<String[]> stealbases = new ArrayList<String[]>();	// 도루

			int count = 0;
			
			for(Element player : players){
				count++;
				
				String[] content = new String[4];
				content[0]	=	player.getElementsByClass("ord").text();	// 순위
				content[1]	=	player.getElementsByTag("strong").text();	// 선수
				content[2]	=	player.getElementsByClass("team").text();	// 팀
				content[3]	=	player.getElementsByTag("em").text();	// 기록
				
				if(count <= 5){
					battings.add(content); // 타율
				}else if(count <= 10){
					rbis.add(content); // 타점
				}else if(count <= 15){
					homeruns.add(content); // 홈런
				}else{
					stealbases.add(content); // 도루
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
