package com.typology.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.typology.entity.entry.Entry;

import static com.typology.namedQueries.NamedQueriesDB.*;

@RepositoryRestResource(path="entry")
public interface EntryRepository extends JpaRepository<Entry, Long>
{	
	
	//jpql query test- works
	@Query(name = "query_jpql")
	List<Entry> findAllOfEnneagramCoreType2();
	
	
	//native query- works 
	@Query(value = GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS, nativeQuery = true)
	List<Entry> findAllOfEnneagramCoreType(@Param("type") int type);
}
