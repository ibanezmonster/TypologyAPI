package com.typology.repository;

import static com.typology.query.TypingQuery.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.typology.entity.entry.Typing;


@Repository
public interface TypingRepository extends JpaRepository<Typing, Long>
{
	@Query(value = FIND_ALL_OF_USER_TYPINGS)
	Optional<List<Typing>> viewAllOfMyTypings(@Param("yname") String typistName);
}
