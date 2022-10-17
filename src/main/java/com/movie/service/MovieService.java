package com.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.model.dto.MovieDTO;
import com.movie.converter.MovieConverter;
import com.movie.feign.MovieFeign;
import com.movie.model.MovieModel;
import com.movie.repository.MovieRepository;
import com.movie.vo.MovieOMDB;

@Service
public class MovieService {
	
	@Value("${imdb.apikey}")
	private String apiKey;
	
	@Autowired
	MovieFeign movieFeign;
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	private MovieConverter movieConverter;
	
	public MovieOMDB getMovie(String tema) {
		return movieFeign.getMovie(tema, apiKey);
	}
	
	public MovieModel getById(Long id) {
		return (MovieModel) movieRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Filme NÃO encontrado!"));
	}
	
	public MovieModel saveMovie(MovieDTO movieDTO) {
		MovieModel movieModel = movieConverter.converterParaFilme(movieDTO);
		return movieRepository.save(movieModel);
	}

}
