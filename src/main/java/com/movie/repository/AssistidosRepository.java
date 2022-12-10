package com.movie.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.movie.model.Assistidos;

@Repository
@Transactional
public interface AssistidosRepository  extends CrudRepository<Assistidos, Long>{

	@Query("select a from Assistidos a where a.pessoaModel.id = ?1")
	public List<Assistidos> getAssistidos(Long id);
	
	@Query("select a from Assistidos a where a.genre like %:genre% ")
	public List<Assistidos> getGenero(String genre);
	
}
