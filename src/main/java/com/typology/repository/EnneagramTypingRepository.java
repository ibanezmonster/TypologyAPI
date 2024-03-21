package com.typology.repository;

import static com.typology.query.EnneagramQuery.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.typology.entity.typologySystem.EnneagramTyping;


@Repository
public interface EnneagramTypingRepository extends JpaRepository<EnneagramTyping, Long>
{
	@Query(value = FIND_ENNEAGRAM_TYPING_ID_BY_ENTRY_NAME)
	Optional<EnneagramTyping> findEnneagramTypingByEntryName(@Param("name") String name);
}
