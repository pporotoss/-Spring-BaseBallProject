package com.baseball.team.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/team")
public class TeamViewController {

	// 경기결과페이지 이동
	@RequestMapping(value="/result/{day}")
	public String viewResult(@PathVariable("day")String day ,Model model){

		ArrayList<String[]> lft;
		ArrayList<String[]> rgt;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		//SimpleDateFormat week = new SimpleDateFormat("EEE");
		
		int gap = 0;
		if(day.equals("yesterday")){
			gap = -1;
		}else if(day.equals("tomorrow")){
			gap = 1;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, gap);
		Date date = cal.getTime();
		
		String today = dateFormat.format(date);
		
		model.addAttribute("today", today);	// 날짜
		
		try {
			Document doc = Jsoup.connect("http://sports.news.naver.com/schedule/scoreBoard.nhn?date="+today+"&category=kbo").get();

			Element ul = doc.getElementById("todaySchedule");
			
			if(ul == null)return "/team/result";
			
			Elements divs = ul.getElementsByTag("div");
			
			Elements states = divs.select(".state");	// 경기상태
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
			
			model.addAttribute("states", states);	// 경기상태
			model.addAttribute("lft", lft);	// 왼쪽팀리스트
			model.addAttribute("rgt", rgt);	// 오른쪽팀 리스트
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/team/result";
	}
	
	
	// 팀순위페이지 이동
	@RequestMapping("/ranking")
	public String viewRanking(Model model){
		
		try {
			Document doc = Jsoup.connect("http://sports.news.naver.com/kbaseball/record/index.nhn").get();

			Element table = doc.getElementById("regularTeamRecordList_table");

			Elements rank = table.getElementsByTag("tr");

			ArrayList<String[]> rankList = new ArrayList<String[]>();

			for(Element content : rank){
				String[] team = content.text().split(" ");
				rankList.add(team);
			}
			
			model.addAttribute("rankList", rankList);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return"/team/ranking";
	}
	
	
	// 투수순위 페이지 이동
	@RequestMapping("/pitcher")
	public String viewPitcher(Model model){
		
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
			
			model.addAttribute("wins", wins);
			model.addAttribute("earned", earned);
			model.addAttribute("strikes", strikes);
			model.addAttribute("saves", saves);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/team/pitcher";
	}
	
	
	// 타자 순위 페이지 이동
	@RequestMapping("/hitter")
	public String viewHitter(Model model){
		
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
			
			model.addAttribute("battings", battings);
			model.addAttribute("rbis", rbis);
			model.addAttribute("homeruns", homeruns);
			model.addAttribute("stealbases", stealbases);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return"/team/hitter";
	}
}
