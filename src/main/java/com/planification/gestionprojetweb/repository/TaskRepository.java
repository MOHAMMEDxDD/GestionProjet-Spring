package com.planification.gestionprojetweb.repository;

import com.planification.gestionprojetweb.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    long countByStatut(String statut); // Pour Admin
    
    List<Task> findByResponsableId(Long id); // Pour Dev
    long countByResponsableIdAndStatut(Long id, String statut); // Stats Dev
}