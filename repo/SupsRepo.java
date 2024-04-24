package com.ordermanagement.repo;

import com.ordermanagement.model.Sups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupsRepo extends JpaRepository<Sups, Long> {
    List<Sups> findAllByNameContaining(String name);
}
