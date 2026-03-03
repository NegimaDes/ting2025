package com.example.demo.repository;

import com.example.demo.model.ClientEntity;
import com.example.demo.model.LoanEntity;
import com.example.demo.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findByClientAndStatus(ClientEntity client, LoanStatus status);

    List<LoanEntity> findByStatusIn(Collection<LoanStatus> statuses);

    @Query("SELECT l FROM LoanEntity l WHERE l.status IN ('ACTIVE','OVERDUE')")
    List<LoanEntity> findByStatusIn(List<String> statuses);

}
