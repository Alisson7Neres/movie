package com.movie.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.movie.model.PessoaModel;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {

	@Query("select p from PessoaModel p where p.email = ?1")
	PessoaModel findUserByLogin(String email);

	/* descobrir o nome da constraint para remover */
	@Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'pessoas_role' and column_name = 'role_id' and constraint_name <> 'unique_role_user';", nativeQuery = true)
	String consultaConstraintRole();

	@Modifying
	@Transactional
	@Query(value = "update PessoaModel set filme = :filme where nome = :nome")
	void salvarAssistido(String filme, String nome);

	@Query("select p from PessoaModel p where p.email = ?1")
	Optional<PessoaModel> findByNome(String email);

}
