package com.ordermanagement.repo;

import com.ordermanagement.model.Contracts;
import com.ordermanagement.model.enums.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractsRepo extends JpaRepository<Contracts, Long> {
    List<Contracts> findAllByStatus(ContractStatus status);

    List<Contracts> findAllByStatusAndSup_Id(ContractStatus status, Long supId);

    List<Contracts> findAllByStatusAndOwner_Id(ContractStatus status, Long ownerId);
}
