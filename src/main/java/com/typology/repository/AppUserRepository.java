package com.typology.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.typology.entity.user.AppUser;
import static com.typology.query.AppUserQuery.*;



@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>
{
	@Query(value=GET_USER_BY_USERNAME)
	Optional<AppUser> findByName(@Param("username") String name);			
}
