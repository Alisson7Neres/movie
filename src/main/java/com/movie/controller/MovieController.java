package com.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.model.dto.MovieDTO;
import com.model.dto.MovieDTO;
import com.movie.converter.MovieConverter;
import com.movie.service.MovieService;
import com.movie.vo.MovieOMDB;
import com.movie.vo.MovieVO;

@Controller
public class MovieController {
	

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private MovieConverter movieConverter;
	
	@GetMapping("/omdb/{tema}")
	public ResponseEntity<MovieOMDB> getMovie(@PathVariable String tema) {
		
		try {
			
		MovieOMDB movieOMDB = movieService.getMovie(tema);
		return ResponseEntity.status(HttpStatus.OK).body(movieOMDB);
		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping()
	public ResponseEntity<MovieVO> saveMovie(@RequestBody MovieDTO movieDTO) {
		
		try {
			
		MovieVO movieVO = movieConverter.converterParaFilmeVO(movieService.saveMovie(movieDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(movieVO);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}

}
