package com.planification.gestionprojetweb.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.planification.gestionprojetweb.model.Project;
import com.planification.gestionprojetweb.model.Task;
import com.planification.gestionprojetweb.model.User;
import com.planification.gestionprojetweb.repository.ProjectRepository;
import com.planification.gestionprojetweb.repository.TaskRepository;
import com.planification.gestionprojetweb.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProjectController {

    @Autowired private ProjectRepository projectRepository;
    @Autowired private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;

    // ==========================================
    // 1. MANAGER DASHBOARD (Project Manager)
    // ==========================================
    @GetMapping("/dashboard-responsable")
    public String dashboard(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Project> projects;
        
        // Search Logic
        if (keyword != null && !keyword.isEmpty()) {
            projects = projectRepository.searchProjects(keyword);
        } else {
            projects = projectRepository.findAll();
        }

        model.addAttribute("projects", projects);
        // Filter: Retrieve only Developers for task assignment
        model.addAttribute("developers", userRepository.findByRole("DEV"));
        
        // Statistics for Chart.js
        model.addAttribute("totalTasks", taskRepository.count());
        model.addAttribute("cntEnRetard", taskRepository.countByStatut("EN_RETARD"));
        model.addAttribute("cntEnCours", taskRepository.countByStatut("EN_COURS"));
        model.addAttribute("cntTermine", taskRepository.countByStatut("TERMINE"));
        model.addAttribute("cntAFaire", taskRepository.countByStatut("A_FAIRE"));
        
        return "dashboard-responsable";
    }

    // ==========================================
    // 2. DEVELOPER DASHBOARD
    // ==========================================
    @GetMapping("/dashboard-developpeur")
    public String dashboardDeveloppeur(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Long userId = user.getId();
        // Retrieve tasks assigned to the current developer
        List<Task> myTasks = taskRepository.findByResponsableId(userId);
        
        // Performance KPIs
        long total = myTasks.size();
        long done = taskRepository.countByResponsableIdAndStatut(userId, "TERMINE");
        long inProgress = taskRepository.countByResponsableIdAndStatut(userId, "EN_COURS");
        int performance = (total > 0) ? (int)((done * 100) / total) : 0;

        model.addAttribute("tasks", myTasks);
        model.addAttribute("user", user);
        model.addAttribute("countTotal", total);
        model.addAttribute("countDone", done);
        model.addAttribute("countProgress", inProgress);
        model.addAttribute("performance", performance);
        
        return "dashboard-developpeur";
    }

    @PostMapping("/task/updateStatus")
    public String updateTaskStatus(@RequestParam Long taskId, @RequestParam String statut) {
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task != null) {
            task.setStatut(statut);
            // Auto-update progression based on status
            if(statut.equals("EN_COURS")) task.setProgression(50);
            if(statut.equals("TERMINE")) task.setProgression(100);
            taskRepository.save(task);
        }
        return "redirect:/dashboard-developpeur";
    }

    // ==========================================
    // 3. CRUD OPERATIONS & DETAILS
    // ==========================================
    @GetMapping("/project/details/{id}")
    public String projectDetails(@PathVariable Long id, Model model) {
        Project project = projectRepository.findById(id).orElse(null);
        if(project != null) {
            model.addAttribute("project", project);
            model.addAttribute("tasks", project.getTasks());
        }
        return "project-details";
    }

    @PostMapping("/project/add")
    public String addProject(@ModelAttribute Project project) {
        project.setStatut("EN_COURS");
        projectRepository.save(project);
        return "redirect:/dashboard-responsable";
    }

    @PostMapping("/task/add")
    public String addTask(@RequestParam String titre, @RequestParam Long projectId, @RequestParam Long responsableId, @RequestParam String dateEcheance) {
        Task task = new Task();
        task.setTitre(titre); 
        task.setStatut("A_FAIRE"); 
        task.setProgression(0);
        task.setDateEcheance(LocalDate.parse(dateEcheance));
        
        task.setProject(projectRepository.findById(projectId).orElse(null));
        task.setResponsable(userRepository.findById(responsableId).orElse(null));
        
        taskRepository.save(task);
        return "redirect:/dashboard-responsable";
    }

    @PostMapping("/member/add")
    public String addMember(User user) {
        user.setRole("DEV");
        userRepository.save(user);
        return "redirect:/dashboard-responsable";
    }

    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/dashboard-responsable"; 
    }

    @GetMapping("/project/archive/{id}")
    public String archiveProject(@PathVariable Long id) {
        Project p = projectRepository.findById(id).orElse(null);
        if(p != null) { 
            p.setStatut("ARCHIVE"); 
            projectRepository.save(p); 
        }
        return "redirect:/dashboard-responsable";
    }

    // ==========================================
    // 4. EDIT FEATURES (Modification)
    // ==========================================
    
    // Edit Project
    @GetMapping("/project/edit/{id}")
    public String showEditProject(@PathVariable Long id, Model model) {
        Project p = projectRepository.findById(id).orElse(null);
        if (p != null) {
            model.addAttribute("project", p);
            return "edit-project";
        }
        return "redirect:/dashboard-responsable";
    }

    @PostMapping("/project/update")
    public String updateProject(@ModelAttribute Project project) {
        projectRepository.save(project);
        return "redirect:/dashboard-responsable";
    }

    // Edit Task
    @GetMapping("/task/edit/{id}")
    public String showEditTask(@PathVariable Long id, Model model) {
        Task t = taskRepository.findById(id).orElse(null);
        if (t != null) {
            model.addAttribute("task", t);
            model.addAttribute("projects", projectRepository.findAll());
            model.addAttribute("developers", userRepository.findByRole("DEV"));
            return "edit-task";
        }
        return "redirect:/dashboard-responsable";
    }

    @PostMapping("/task/update")
    public String updateTask(@ModelAttribute Task task, @RequestParam Long projectId, @RequestParam Long responsableId) {
        task.setProject(projectRepository.findById(projectId).orElse(null));
        task.setResponsable(userRepository.findById(responsableId).orElse(null));
        taskRepository.save(task);
        return "redirect:/project/details/" + projectId;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); return "redirect:/login";
    }
}