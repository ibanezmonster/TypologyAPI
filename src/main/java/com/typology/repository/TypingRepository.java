package com.typology.repository;

import static com.typology.query.TypingQuery.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.typology.entity.entry.Entry;
import com.typology.entity.entry.Typing;
import com.typology.entity.typologySystem.TypologySystem;
import com.typology.entity.user.Typist;


@Repository
public interface TypingRepository extends JpaRepository<Typing, Long>
{
	@Query(value = FIND_ALL_OF_USER_TYPINGS)	
	Optional<List<Typing>> viewAllOfMyTypings(@Param("yname") String typistName);

	@Query(value = FIND_USER_TYPING_BY_ENTRY_AND_SYSTEM)	
	Optional<Typing> findTypingByTypistAndEntryAndTypologySystemName(@Param("yname") String typist,
															 		@Param("ename") String entry,
															 		@Param("zname") String typologySystem);
	
//	
//	@Query(value = DELETE_USER_TYPING_BY_ENTRY_AND_SYSTEM)	
//	void deleteTypingByTypistAndEntryAndTypologySystemName(@Param("yname") String typist,
//															 		@Param("ename") String entry,
//															 		@Param("zname") String typologySystem);
}
