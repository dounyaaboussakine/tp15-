package com.example.banque_service.repositories;



import com.example.banque_service.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompteRepository extends JpaRepository<Compte, Long> {

    // Somme des soldes, retourne un objet Double pour gérer les cas null
    @Query("SELECT SUM(c.solde) FROM Compte c")
    Double sumSoldes();
}
