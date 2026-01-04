package com.planification.gestionprojetweb.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.planification.gestionprojetweb.model.Task;
import com.planification.gestionprojetweb.repository.TaskRepository;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // الصفحة الرئيسية
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "index";
    }

    // زيادة مهمة جديدة
    @PostMapping("/add")
    public String addTask(@RequestParam String title, 
                          @RequestParam String description,
                          @RequestParam String responsable,
                          @RequestParam String deadline) {
        Task t = new Task(title, description, LocalDate.parse(deadline), "En cours", responsable);
        taskRepository.save(t);
        return "redirect:/";
    }

    // مسح مهمة
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/";
    }
}