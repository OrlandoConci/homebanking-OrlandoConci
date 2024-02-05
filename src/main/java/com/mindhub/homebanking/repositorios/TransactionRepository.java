package com.mindhub.homebanking.repositorios;

import com.mindhub.homebanking.modelos.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
