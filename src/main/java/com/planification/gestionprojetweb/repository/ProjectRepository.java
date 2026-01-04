package com.planification.gestionprojetweb.repository;

import com.planification.gestionprojetweb.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    // النقطة 13: البحث بالسمية أو الوصف
    @Query("SELECT p FROM Project p WHERE p.nom LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Project> searchProjects(@Param("keyword") String keyword);

    // النقطة 12: المكتبة (الأرشيف)
    List<Project> findByStatut(String statut);
}