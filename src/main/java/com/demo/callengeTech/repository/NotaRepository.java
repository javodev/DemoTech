package com.demo.callengeTech.repository;

import com.demo.callengeTech.entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<NotaEntity, Integer> {
    @Query(value = "SELECT t FROM NotaEntity t where t.personid = ?1")
    List<NotaEntity> findByPersonId(String id);
}
