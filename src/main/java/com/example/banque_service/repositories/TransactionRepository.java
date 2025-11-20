package com.example.banque_service.repositories;



import com.example.banque_service.entities.Transaction;
import com.example.banque_service.entities.Compte;
import com.example.banque_service.entities.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCompte(Compte compte);

    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.type = ?1")
    Double sumByType(TypeTransaction type);
}

