package com.example.banque_service.controller;



import com.example.banque_service.dto.TransactionRequest;
import com.example.banque_service.entities.*;

import com.example.banque_service.repositories.CompteRepository;
import com.example.banque_service.repositories.TransactionRepository;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class BanqueGraphQLController {

    private final CompteRepository compteRepository;
    private final TransactionRepository transactionRepository;

    public BanqueGraphQLController(CompteRepository compteRepository,
                                   TransactionRepository transactionRepository) {
        this.compteRepository = compteRepository;
        this.transactionRepository = transactionRepository;
    }

    // Mutation pour ajouter une transaction à un compte
    @MutationMapping
    public Transaction addTransaction(@Argument("transaction") TransactionRequest transactionRequest) {
        if (transactionRequest == null) {
            throw new RuntimeException("TransactionRequest cannot be null");
        }

        Compte compte = compteRepository.findById(transactionRequest.getCompteId())
                .orElseThrow(() -> new RuntimeException("Compte not found"));

        Transaction transaction = new Transaction();
        transaction.setMontant(transactionRequest.getMontant());
        transaction.setDate(transactionRequest.getDate());
        transaction.setType(transactionRequest.getType());
        transaction.setCompte(compte);

        transactionRepository.save(transaction);
        return transaction;
    }

    // Query pour afficher toutes les transactions d'un compte
    @QueryMapping
    public List<Transaction> compteTransactions(@Argument Long id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte not found"));
        return transactionRepository.findByCompte(compte);
    }

    // Query pour afficher toutes les transactions
    @QueryMapping
    public List<Transaction> allTransactions() {
        return transactionRepository.findAll();
    }

    // Query pour les statistiques globales sur les transactions
    @QueryMapping
    public Map<String, Object> transactionStats() {
        long count = transactionRepository.count();
        double sumDepots = transactionRepository.sumByType(TypeTransaction.DEPOT) != null ?
                transactionRepository.sumByType(TypeTransaction.DEPOT) : 0.0;
        double sumRetraits = transactionRepository.sumByType(TypeTransaction.RETRAIT) != null ?
                transactionRepository.sumByType(TypeTransaction.RETRAIT) : 0.0;

        return Map.of(
                "count", count,
                "sumDepots", sumDepots,
                "sumRetraits", sumRetraits
        );
    }
}

