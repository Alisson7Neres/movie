package com.movie.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model.dto.MovieDTO;
import com.movie.converter.MovieConverter;
import com.movie.service.MovieService;
import com.movie.vo.MovieOMDB;
import com.movie.vo.MovieVO;

@Controller
@RequestMapping("/filmes")
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

	@GetMapping("/{id}")
	public ResponseEntity<MovieVO> getById(@PathVariable("id") Long id) {

		try {
			MovieVO movieVO = movieConverter.converterParaFilmeVO(movieService.getById(id));
			return ResponseEntity.ok(movieVO);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping
	public ResponseEntity<MovieVO> saveMovie(@RequestBody MovieDTO movieDTO) {

		try {

			MovieVO movieVO = movieConverter.converterParaFilmeVO(movieService.saveMovie(movieDTO));
			
			addHateoas(movieVO);
			return ResponseEntity.status(HttpStatus.CREATED).body(movieVO);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
		movieService.deleteMovie(id);
		return ResponseEntity.noContent().build();
	}

	private void addHateoas(MovieVO movieVO) {
		movieVO.add(linkTo(methodOn(MovieController.class).getById(movieVO.getId())).withSelfRel());
	}

}
