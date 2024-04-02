package com.typology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.typology.entity.user.Authority;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authority, Long>
{

}
