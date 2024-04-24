package com.typology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.typology.entity.TestEntity;
import com.typology.entity.entry.Typing;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long>
{

}
