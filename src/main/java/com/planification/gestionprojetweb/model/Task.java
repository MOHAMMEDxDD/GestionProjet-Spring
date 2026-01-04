package com.planification.gestionprojetweb.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks") // الطابل سميتو "tasks"
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private String statut; // A_FAIRE, EN_COURS, TERMINE, EN_RETARD
    private LocalDate dateEcheance;
    private int progression;
    private boolean urgent;

    // الربط مع الديفلوبر (Point 1 & 5)
    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private User responsable;

    // الربط مع المشروع (Point 8)
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Task() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public LocalDate getDateEcheance() { return dateEcheance; }
    public void setDateEcheance(LocalDate dateEcheance) { this.dateEcheance = dateEcheance; }
    public int getProgression() { return progression; }
    public void setProgression(int progression) { this.progression = progression; }
    public boolean isUrgent() { return urgent; }
    public void setUrgent(boolean urgent) { this.urgent = urgent; }
    public User getResponsable() { return responsable; }
    public void setResponsable(User responsable) { this.responsable = responsable; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
}