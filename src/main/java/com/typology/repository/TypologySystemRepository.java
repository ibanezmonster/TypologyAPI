package com.typology.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.user.Typist;

import static com.typology.query.TypologySystemQuery.*;

@Repository
public interface TypologySystemRepository extends JpaRepository<TypologySystem, Long>
{
	@Query(value = FIND_TYPOLOGY_SYSTEM_BY_NAME)
	Optional<TypologySystem> findByName(@Param("name") String name);			
}
