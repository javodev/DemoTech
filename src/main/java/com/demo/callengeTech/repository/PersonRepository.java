package com.demo.callengeTech.repository;

import com.demo.callengeTech.entity.NotaEntity;
import com.demo.callengeTech.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
    @Query(value = "SELECT t FROM PersonEntity t where t.id = ?1")
    PersonEntity findByPersonId(String id);
}
