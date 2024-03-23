package com.typology.repository;

import static com.typology.query.EnneagramQuery.*;
import static com.typology.query.TypingQuery.FIND_USER_ENNEAGRAM_TYPING_BY_NAME;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.typology.entity.typologySystem.EnneagramTyping;


@Repository
public interface EnneagramTypingRepository extends JpaRepository<EnneagramTyping, Long>
{
//	@Query(value = FIND_ENNEAGRAM_TYPING_BY_ENTRY_NAME_AND_TYPIST_NAME)
//	Optional<EnneagramTyping> findEnneagramTypingByEntryName(@Param("yname") String typistName);
	
	@Query(value = FIND_USER_ENNEAGRAM_TYPING_BY_NAME)
	Optional<EnneagramTyping> findEnneagramTypingByTypistAndEntryName(@Param("yname") String typistName, 
														 			  @Param("ename") String entryName);
}
