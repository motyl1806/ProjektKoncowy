package com.example.finalproject.Projects;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/projects")

public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
    }

    // TODO: @Secured("ROLE_PROJECTS_TAB")
    @GetMapping("/index")
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("projects/index");

        modelAndView.addObject("projects", projectRepository.findAll());

        return modelAndView;
    }
    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project project = new Project();
        modelAndView.addObject("project", project);

        return modelAndView;
    }
    @GetMapping ("/edit/{id}")
    ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projects/create");

        Project project = projectRepository.findById(id).orElse(null);

        modelAndView.addObject("project",project);

        return modelAndView;

    }
    @GetMapping("/remove/{id}")
    ModelAndView remove(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projects/remove");

        Project project = projectRepository.findById(id).orElse(null);

        modelAndView.addObject("project",project);

        return modelAndView;
    }
    @PostMapping("/save")
    String save(@ModelAttribute Project project) {

        boolean isNew = project.getId() == null;

        projectRepository.save(project);

        if (isNew) {
            return "redirect:/projects/index/";
        } else {
            return "redirect:/projects/edit/" + project.getId();
        }
    }
}