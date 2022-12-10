package com.movie.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.movie.model.Lista;

@Repository
@Transactional
public interface ListaRepository  extends CrudRepository<Lista, Long>{

	@Query("select a from Lista a where a.pessoaModel.id = ?1")
	public List<Lista> getLista(Long id);

	@Query("select a from Lista a where a.genre like %:genre% ")
	public List<Lista> getGenero(String genre);
	
}
