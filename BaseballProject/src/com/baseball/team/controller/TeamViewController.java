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

	// ����������� �̵�
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
		
		model.addAttribute("today", today);	// ��¥
		
		try {
			Document doc = Jsoup.connect("http://sports.news.naver.com/schedule/scoreBoard.nhn?date="+today+"&category=kbo").get();

			Element ul = doc.getElementById("todaySchedule");
			
			if(ul == null)return "/team/result";
			
			Elements divs = ul.getElementsByTag("div");
			
			Elements states = divs.select(".state");	// ������
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
			
			model.addAttribute("states", states);	// ������
			model.addAttribute("lft", lft);	// ����������Ʈ
			model.addAttribute("rgt", rgt);	// �������� ����Ʈ
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/team/result";
	}
	
	
	// ������������ �̵�
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
	
	
	// �������� ������ �̵�
	@RequestMapping("/pitcher")
	public String viewPitcher(Model model){
		
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
	
	
	// Ÿ�� ���� ������ �̵�
	@RequestMapping("/hitter")
	public String viewHitter(Model model){
		
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
