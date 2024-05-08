package com.typology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.typology.entity.TestMappingsEntity;
import com.typology.entity.entry.Typing;

@Repository
public interface TestMappingsRepository extends JpaRepository<TestMappingsEntity, Long>
{

}
