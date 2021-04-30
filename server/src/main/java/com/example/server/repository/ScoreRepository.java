package com.example.server.repository;

import com.example.server.model.Player;
import com.example.server.model.Score;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {

    List<Score> findAllByOrderByScoreDesc();//not sure with this one
    List<Score> findAllByScoreTimeBetweenOrderByScoreDesc(
            Date publicationTimeStart,
            Date publicationTimeEnd);
    //List<Score> findAllByPlayerOrderByScoreDesc(Player player);

}

