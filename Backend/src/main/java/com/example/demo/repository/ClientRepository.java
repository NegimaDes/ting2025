package com.example.demo.repository;

import com.example.demo.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    ClientEntity findByRut(String rut);
    ClientEntity findByEmail(String email);

    @Query("SELECT DISTINCT l.client FROM LoanEntity l WHERE l.status = 'OVERDUE'")
    List<ClientEntity> findClientsWithOverdueLoans();

}
