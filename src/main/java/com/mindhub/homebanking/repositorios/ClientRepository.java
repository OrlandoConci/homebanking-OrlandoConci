package com.mindhub.homebanking.repositorios;

import com.mindhub.homebanking.modelos.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
