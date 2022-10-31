package com.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.movie.model.PessoaModel;

@Repository
@EnableJpaRepositories
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {

}
