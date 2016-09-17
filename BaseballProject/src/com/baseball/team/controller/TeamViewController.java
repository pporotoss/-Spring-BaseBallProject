package com.baseball.team.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baseball.team.model.service.TeamService;

@Controller
@RequestMapping("/team")
public class TeamViewController {
	
	@Autowired
	TeamService teamService;

	// 경기결과페이지 이동
	@RequestMapping(value="/result/{day}")
	public String viewResult(@PathVariable("day")String day ,Model model){

		Map<String, Object> scheduleMap = teamService.getTodaySchedule(day);
		
		Elements states = (Elements) scheduleMap.get("states");
		List lft = (List) scheduleMap.get("lft");
		List rgt = (List) scheduleMap.get("rgt");
		
		model.addAttribute("states", states);	// 경기상태
		model.addAttribute("lft", lft);	// 왼쪽팀리스트
		model.addAttribute("rgt", rgt);	// 오른쪽팀 리스트
		
		String yyyy = day.substring(0,4);
		String MM = day.substring(4,6);
		String dd = day.substring(6);
		
		day = yyyy+"/"+MM+"/"+dd;
		
		model.addAttribute("day", day);	
		
		return "/team/result";
	}
	
	
	// 팀순위페이지 이동
	@RequestMapping("/teamRanking")
	public String viewTeamRanking(Model model){

		List rankList = teamService.getTeamRanking();
		model.addAttribute("rankList", rankList);
		
		return"/team/ranking";
	}
	
	
	// 투수순위 페이지 이동
	@RequestMapping("/pitcherRanking")
	public String viewPitcherRanking(Model model){
		
		Map<String, ArrayList<String[]>> pitcherMap = teamService.getPitcherRanking();
		
		List wins = pitcherMap.get("wins");
		List earned = pitcherMap.get("earned");
		List strikes = pitcherMap.get("strikes");
		List saves = pitcherMap.get("saves");

		model.addAttribute("wins", wins);
		model.addAttribute("earned", earned);
		model.addAttribute("strikes", strikes);
		model.addAttribute("saves", saves);
		
		return "/team/pitcher";
	}
	
	
	// 타자 순위 페이지 이동
	@RequestMapping("/hitterRanking")
	public String viewHitterRanking(Model model){
		
		Map<String, ArrayList<String[]>> hitterMap = teamService.getHitterRanking(); 
		
		List battings = hitterMap.get("battings");	// 타율
		List rbis = hitterMap.get("rbis");	// 타점
		List homeruns = hitterMap.get("homeruns");	// 홈런
		List stealbases = hitterMap.get("stealbases");	// 도루
		
		model.addAttribute("battings", battings);
		model.addAttribute("rbis", rbis);
		model.addAttribute("homeruns", homeruns);
		model.addAttribute("stealbases", stealbases);
		
		return"/team/hitter";
	}
}
