package com.movie.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model.dto.MovieDTO;
import com.movie.converter.MovieConverter;
import com.movie.service.MovieService;
import com.movie.vo.MovieOMDB;
import com.movie.vo.MovieVO;

@Controller
//@RequestMapping("/filmes")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@Autowired
	private MovieConverter movieConverter;

	List<MovieOMDB> movieOMDBs = new ArrayList<>();

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/minhaconta")
	public String minhaConta() {
		return "minhaconta";
	}

	@GetMapping("/omdb/{tema}")
	public ResponseEntity<MovieOMDB> getMovie(@PathVariable String tema) {

		try {

			MovieOMDB movieOMDB = movieService.getMovie(tema);
			return ResponseEntity.status(HttpStatus.OK).body(movieOMDB);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	String titulo = "";
	String year = "";
	String released = "";
	String runtime = "";
	String genre = "";
	String language = "";
	String country = "";
	String poster = "";
	String rated = "";
	String imdbRating = "";
	String imdbID = "";
	String plot = "";

	@PostMapping("/pesquisar")
	public String postMoviePesquisa(@RequestParam("consultar") String pegarTitulo) {

		titulo = pegarTitulo;
		MovieOMDB movieOMDB = movieService.getMovie(titulo);
		ModelAndView andView = new ModelAndView();
		andView.addObject("consultar", movieOMDB);

		titulo = movieOMDB.getTitle();
		year = movieOMDB.getYear();
		released = movieOMDB.getReleased();
		runtime = movieOMDB.getRuntime();
		genre = movieOMDB.getGenre();
		language = movieOMDB.getLanguage();
		country = movieOMDB.getCountry();
		poster = movieOMDB.getPoster();
		rated = movieOMDB.getRated();
		imdbRating = movieOMDB.getImdbRating();
		imdbID = movieOMDB.getImdbID();
		plot = movieOMDB.getPlot();

		movieOMDB.setTitle(titulo);
		movieOMDB.setYear(year);
		movieOMDB.setReleased(released);
		movieOMDB.setRuntime(runtime);
		movieOMDB.setGenre(genre);
		movieOMDB.setLanguage(language);
		movieOMDB.setCountry(country);
		movieOMDB.setPoster(poster);
		movieOMDB.setRated(rated);
		movieOMDB.setImdbRating(imdbRating);
		movieOMDB.setImdbID(imdbID);
		movieOMDB.setPlot(plot);

		System.out.println(imdbID);

		return "redirect:/filmes";

	}

	@GetMapping("/filmes")
	public String getMovie(ModelMap model) {

		model.addAttribute("title", titulo);
		model.addAttribute("year", year);
		model.addAttribute("released", released);
		model.addAttribute("runtime", runtime);
		model.addAttribute("genre", genre);
		model.addAttribute("language", language);
		model.addAttribute("country", country);
		model.addAttribute("poster", poster);
		model.addAttribute("rated", rated);
		model.addAttribute("imdbRating", imdbRating);
		model.addAttribute("plot", plot);
		return "pesquisar";

	}

	long number = 0;
	String tt = "";

	@PostMapping("/random/movie")
	public String randomMovie(@RequestParam("consultar") String imdbID) {

		number = (long) (1000000l + Math.random() * 8999999l);
		String tt = "tt" + number;

		imdbID = tt;
		MovieOMDB movieOMDB = movieService.getRandomMovie(imdbID);
		System.out.println(imdbID);

		ModelAndView andView = new ModelAndView();
		andView.addObject("consultar", movieOMDB);

		while (titulo == null) {
			number = (long) (1000000l + Math.random() * 8999999l);
			tt = "tt" + number;
			imdbID = tt;
			movieOMDB = movieService.getRandomMovie(imdbID);

			if (movieOMDB.getTitle() != null) {
				titulo = movieOMDB.getTitle();
				year = movieOMDB.getYear();
				released = movieOMDB.getReleased();
				runtime = movieOMDB.getRuntime();
				genre = movieOMDB.getGenre();
				language = movieOMDB.getLanguage();
				country = movieOMDB.getCountry();
				poster = movieOMDB.getPoster();
				rated = movieOMDB.getRated();
				imdbRating = movieOMDB.getImdbRating();
				imdbID = movieOMDB.getImdbID();
				plot = movieOMDB.getPlot();

				movieOMDB.setTitle(titulo);
				movieOMDB.setYear(year);
				movieOMDB.setReleased(released);
				movieOMDB.setRuntime(runtime);
				movieOMDB.setGenre(genre);
				movieOMDB.setLanguage(language);
				movieOMDB.setCountry(country);
				movieOMDB.setPoster(poster);
				movieOMDB.setRated(rated);
				movieOMDB.setImdbRating(imdbRating);
				movieOMDB.setImdbID(imdbID);
				movieOMDB.setPlot(plot);
				
				return "redirect:/random";
			}
		}

		if (titulo != null) {
			titulo = movieOMDB.getTitle();
			year = movieOMDB.getYear();
			released = movieOMDB.getReleased();
			runtime = movieOMDB.getRuntime();
			genre = movieOMDB.getGenre();
			language = movieOMDB.getLanguage();
			country = movieOMDB.getCountry();
			poster = movieOMDB.getPoster();
			rated = movieOMDB.getRated();
			imdbRating = movieOMDB.getImdbRating();
			imdbID = movieOMDB.getImdbID();
			plot = movieOMDB.getPlot();

			movieOMDB.setTitle(titulo);
			movieOMDB.setYear(year);
			movieOMDB.setReleased(released);
			movieOMDB.setRuntime(runtime);
			movieOMDB.setGenre(genre);
			movieOMDB.setLanguage(language);
			movieOMDB.setCountry(country);
			movieOMDB.setPoster(poster);
			movieOMDB.setRated(rated);
			movieOMDB.setImdbRating(imdbRating);
			movieOMDB.setImdbID(imdbID);
			movieOMDB.setPlot(plot);
		}
		return "redirect:/random";
	}

	@GetMapping("/random")
	public String getRandomMovie(ModelMap model) {
		model.addAttribute("title", titulo);
		model.addAttribute("year", year);
		model.addAttribute("released", released);
		model.addAttribute("runtime", runtime);
		model.addAttribute("genre", genre);
		model.addAttribute("language", language);
		model.addAttribute("country", country);
		model.addAttribute("poster", poster);
		model.addAttribute("rated", rated);
		model.addAttribute("imdbRating", imdbRating);
		model.addAttribute("plot", plot);
		model.addAttribute("imdbID", tt);
		return "random";
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
