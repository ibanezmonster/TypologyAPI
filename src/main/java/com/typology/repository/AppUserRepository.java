package com.typology.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.typology.entity.user.AppUser;



@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>
{
	Optional<AppUser> findByName(String name);			
}
