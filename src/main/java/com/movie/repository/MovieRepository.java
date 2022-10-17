package com.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.movie.model.MovieModel;

@Repository
@EnableJpaRepositories
public interface MovieRepository extends JpaRepository<MovieModel, Long> {

}
