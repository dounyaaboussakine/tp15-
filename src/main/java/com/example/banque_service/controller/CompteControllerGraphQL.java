package com.example.banque_service.controller;



import com.example.banque_service.entities.Compte;
import com.example.banque_service.repositories.CompteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CompteControllerGraphQL {

    private final CompteRepository compteRepository;

    // ---- Query 1 : Tous les comptes ----
    @QueryMapping
    public List<Compte> allComptes() {
        return compteRepository.findAll();
    }

    // ---- Query 2 : Compte par ID ----
    @QueryMapping
    public Compte compteById(@Argument Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte " + id + " non trouvé"));
    }

    // ---- Mutation : Ajouter un compte ----
    @MutationMapping
    public Compte saveCompte(@Argument Compte compte) {
        return compteRepository.save(compte);
    }

    // ---- Query 3 : Statistiques ----
    @QueryMapping
    public Map<String, Object> totalSolde() {
        long count = compteRepository.count();
        Double sum = compteRepository.sumSoldes(); // peut être null si aucun compte
        double average = (count > 0 && sum != null) ? sum / count : 0;

        return Map.of(
                "count", count,
                "sum", sum != null ? sum : 0,
                "average", average
        );
    }
}
