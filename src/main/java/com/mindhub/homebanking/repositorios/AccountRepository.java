package com.mindhub.homebanking.repositorios;

import com.mindhub.homebanking.modelos.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
