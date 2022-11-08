package com.movie.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.movie.model.PessoaModel;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, Long> {
	
	/* Quando iniciar o h2 : insert into role (id, nome_role)  values (1, 'ROLE_USER') */

	@Query("select p from PessoaModel p where p.email = ?1")
	PessoaModel findUserByLogin(String email);
	
	/* descobrir o nome da constraint para remover */
	@Query(value = " select constraint_name from information_schema.constraint_column_usage where table_name = 'pessoas_role' and column_name = 'role_id' and constraint_name <> 'unique_role_user';", nativeQuery = true)
	String consultaConstraintRole();
	
	/* inserir os acessos padrão para o usuário novo cadastro */
	@Transactional
	@Modifying
	@Query(value = "insert into pessoas_role (pessoa_id, role_id) values(?1,(select id from role where nome_role = 'ROLE_USER'));", nativeQuery = true)
	void insereAcessoRolePadrao(Long idUser);
}
