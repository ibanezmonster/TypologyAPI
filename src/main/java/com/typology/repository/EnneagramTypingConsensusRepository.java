package com.typology.repository;

import static com.typology.query.EnneagramQuery.*;
import static com.typology.query.TypingQuery.FIND_USER_ENNEAGRAM_TYPING_BY_NAME;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.typology.entity.typologySystem.EnneagramTyping;
import com.typology.entity.typologySystem.EnneagramTypingConsensus;


@Repository
public interface EnneagramTypingConsensusRepository extends JpaRepository<EnneagramTypingConsensus, Long>
{

}