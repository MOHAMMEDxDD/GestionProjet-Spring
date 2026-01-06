package com.planification.gestionprojetweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planification.gestionprojetweb.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    
    @Query("SELECT p FROM Project p WHERE p.nom LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Project> searchProjects(@Param("keyword") String keyword);

    
    List<Project> findByStatut(String statut);
}