package com.planification.gestionprojetweb.controller;

import com.planification.gestionprojetweb.model.*;
import com.planification.gestionprojetweb.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired private ProjectRepository projectRepository;
    @Autowired private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;

    // === 1. DASHBOARD ADMIN (Avec Filter Devs & Stats) ===
    @GetMapping("/dashboard-responsable")
    public String dashboard(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Project> projects;
        
        // Recherche
        if (keyword != null && !keyword.isEmpty()) {
            projects = projectRepository.searchProjects(keyword);
        } else {
            projects = projectRepository.findAll();
        }

        model.addAttribute("projects", projects);
        
        // HNA FIN KAYN TAGHYIR: Kanjibo ghir les DEV bach n3tiwhom tasks
        model.addAttribute("developers", userRepository.findByRole("DEV"));
        
        // Stats pour le Graphique
        model.addAttribute("totalTasks", taskRepository.count());
        model.addAttribute("cntEnRetard", taskRepository.countByStatut("EN_RETARD"));
        model.addAttribute("cntEnCours", taskRepository.countByStatut("EN_COURS"));
        model.addAttribute("cntTermine", taskRepository.countByStatut("TERMINE"));
        model.addAttribute("cntAFaire", taskRepository.countByStatut("A_FAIRE"));
        
        return "dashboard-responsable";
    }

    // === 2. DASHBOARD DEV ===
    @GetMapping("/dashboard-developpeur")
    public String dashboardDeveloppeur(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Long userId = user.getId();
        List<Task> myTasks = taskRepository.findByResponsableId(userId);
        
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
            if(statut.equals("EN_COURS")) task.setProgression(50);
            if(statut.equals("TERMINE")) task.setProgression(100);
            taskRepository.save(task);
        }
        return "redirect:/dashboard-developpeur";
    }

    // === CRUD Operations ===
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
        task.setTitre(titre); task.setStatut("A_FAIRE"); task.setProgression(0);
        task.setDateEcheance(LocalDate.parse(dateEcheance));
        task.setProject(projectRepository.findById(projectId).orElse(null));
        task.setResponsable(userRepository.findById(responsableId).orElse(null));
        taskRepository.save(task);
        return "redirect:/dashboard-responsable";
    }

    @PostMapping("/member/add")
    public String addMember(User user) {
        user.setRole("DEV");
        userRepository.save(user); // GHADI YSAUVEGARDI POSTE O TEL AUTOMATIQUEMENT
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
        if(p != null) { p.setStatut("ARCHIVE"); projectRepository.save(p); }
        return "redirect:/dashboard-responsable";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); return "redirect:/login";
    }
    // ==========================================
    // 4. MODIFICATION (EDIT FEATURE)
    // ==========================================

    // --- Modifier Projet ---
    @GetMapping("/project/edit/{id}")
    public String showEditProject(@PathVariable Long id, Model model) {
        Project p = projectRepository.findById(id).orElse(null);
        if (p != null) {
            model.addAttribute("project", p);
            return "edit-project"; // Ghadi nsawbo had page db
        }
        return "redirect:/dashboard-responsable";
    }

    @PostMapping("/project/update")
    public String updateProject(@ModelAttribute Project project) {
        // Spring Boot ghadi y3raf bli hada update hit 3ando ID déjà
        projectRepository.save(project); 
        return "redirect:/dashboard-responsable";
    }

    // --- Modifier Tâche ---
    @GetMapping("/task/edit/{id}")
    public String showEditTask(@PathVariable Long id, Model model) {
        Task t = taskRepository.findById(id).orElse(null);
        if (t != null) {
            model.addAttribute("task", t);
            model.addAttribute("projects", projectRepository.findAll());
            model.addAttribute("developers", userRepository.findByRole("DEV")); // Bach tbdel dev
            return "edit-task"; // Ghadi nsawbo had page db
        }
        return "redirect:/dashboard-responsable";
    }

    @PostMapping("/task/update")
    public String updateTask(@ModelAttribute Task task, 
                             @RequestParam Long projectId, 
                             @RequestParam Long responsableId) {
        // Nraj3o lia9a m3a projet o dev
        task.setProject(projectRepository.findById(projectId).orElse(null));
        task.setResponsable(userRepository.findById(responsableId).orElse(null));
        
        taskRepository.save(task);
        return "redirect:/dashboard-responsable";
    }
}