package com.typology.repository;

import static com.typology.namedQueries.NamedQueriesDB.GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.typology.entity.entry.Entry;
import com.typology.entity.info.Teacher;


//@RepositoryRestResource//(path="entry")
@Repository
@Transactional
public interface TeacherRepository extends JpaRepository<Teacher, Long>
{
	//@Query(value = GET_ENNEAGRAM_CORE_TYPE_ALL_RESULTS, nativeQuery = true)
	//List<Entry> findAllOfEnneagramCoreType(@Param("type") int type);
}
