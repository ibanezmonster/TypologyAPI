package com.typology.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.typology.entity.user.Typist;
import static com.typology.query.TypistQuery.*;


@Repository
public interface TypistRepository extends JpaRepository<Typist, Long>
{
	@Query(value=FIND_TYPIST_BY_NAME)
	Optional<Typist> findByName(@Param("name") String name);			
}
