package com.typology.repository;

import static com.typology.query.EnneagramQuery.*;
import static com.typology.query.EntryQuery.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.typology.entity.entry.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long>
{			
	//@Query(value = GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS, nativeQuery = true)			//native query- works
	//@Query(value = GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS)	
	//List<Entry> findAllOfEnneagramCoreType(@Param("type") int type);
	
	
	@Query(value = FIND_ENTRY_BY_NAME)
	Optional<Entry> findByName(@Param("names") String name);
}
