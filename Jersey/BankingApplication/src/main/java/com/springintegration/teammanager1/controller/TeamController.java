package com.springintegration.teammanager1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springintegration.teammanager1.domain.Team;
import com.springintegration.teammanager1.service.TeamService;

@Controller
public class TeamController {
	@Autowired
	private TeamService teamService;

	@RequestMapping(value = "/add")
	public ModelAndView addTeamPage(@ModelAttribute("team")Team team) {
		
		ModelAndView modelAndView = new ModelAndView("add-team-form");
	
		return modelAndView;
	}

	@RequestMapping(value = "/add/process", method = RequestMethod.POST)
	public String addingTeam(@ModelAttribute("team") Team team,
			BindingResult result) {
		
		ModelAndView modelAndView = new ModelAndView("home");
		ModelMap map = new ModelMap();
		
		teamService.addTeam(team);

		String message = "Team was successfully added.";
		modelAndView.addObject("message", message);
		map.addAttribute("message", message);
		return "redirect:/home";
	}

	@RequestMapping(value = "/list")
	public ModelAndView listOfTeams() {
		ModelAndView modelAndView = new ModelAndView("list-of-teams");

		List<Team> teams = teamService.getTeams();
		modelAndView.addObject("teams", teams);

		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editTeamPage(@PathVariable("id") int id,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("edit-team-form");

		Team team1 = teamService.getTeam(id);
		redirectAttributes.addFlashAttribute("team", team1);
		modelAndView.addObject("team", team1);
		return "redirect:/edit-team-form";
	}

	@RequestMapping(value = "/edit-team-form")
	public String forwardPage(@ModelAttribute("team") Team team) {
		return "edit-team-form";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String edditingTeam(@ModelAttribute("team") Team team,
			@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		
		ModelAndView modelAndView = new ModelAndView("home");

		teamService.updateTeam(team, id);

		String message = "Team was successfully edited.";
		modelAndView.addObject("message", message);
		redirectAttributes.addFlashAttribute("message",
				"team was successfully edited");
		return "redirect:/home";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteTeam(@PathVariable("id") int id,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message",
				"team was successfully deleted");

		teamService.deleteTeam(id);
		String message = "Team was successfully deleted.";

		return "redirect:/home";
	}

	@RequestMapping(value = "/home")
	public String indexPage() {
		
		return "home";
	}

}