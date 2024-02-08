package com.mindhub.homebanking.repositorios;

import com.mindhub.homebanking.modelos.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
