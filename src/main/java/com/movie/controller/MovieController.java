package com.movie.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.movie.model.Assistidos;
import com.movie.model.Lista;
import com.movie.model.PessoaModel;
import com.movie.repository.AssistidosRepository;
import com.movie.repository.ListaRepository;
import com.movie.repository.PessoaRepository;
import com.movie.security.ImplementacaoUserDetailsService;
import com.movie.service.MovieService;
import com.movie.vo.MovieOMDB;
import com.movie.vo.MovieVO;

@Controller
public class MovieController {

	@Autowired
	private MovieService movieService;

	@Autowired
	private AssistidosRepository assistidosRepository;

	@Autowired
	private ListaRepository listaRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private MovieConverter movieConverter;

	List<MovieOMDB> movieOMDBs = new ArrayList<>();

	public MovieService getMovieService() {
		return movieService;
	}

	@GetMapping(value = "/")
	public String index() {
		return "index";
	}

	@GetMapping(value = "/cadastrar")
	public String cadastrar() {
		return "cadastrar";
	}

	@GetMapping(value = "/omdb/{tema}")
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
	String director = "";
	String actors = "";
	String language = "";
	String country = "";
	String poster = "";
	String rated = "";
	String imdbRating = "";
	String imdbID = "";
	String plot = "";
	
	public void setGetMovie(@RequestParam("consultar") String pegarTitulo) {
		
		titulo = pegarTitulo;
		MovieOMDB movieOMDB = movieService.getMovie(titulo);
		ModelAndView andView = new ModelAndView();
		andView.addObject("consultar", movieOMDB);
		
		titulo = movieOMDB.getTitle();
		year = movieOMDB.getYear();
		released = movieOMDB.getReleased();
		runtime = movieOMDB.getRuntime();
		genre = movieOMDB.getGenre();
		director = movieOMDB.getDirector();
		actors = movieOMDB.getActors();
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
		movieOMDB.setDirector(director);
		movieOMDB.setActors(actors);
		movieOMDB.setLanguage(language);
		movieOMDB.setCountry(country);
		movieOMDB.setPoster(poster);
		movieOMDB.setRated(rated);
		movieOMDB.setImdbRating(imdbRating);
		movieOMDB.setImdbID(imdbID);
		movieOMDB.setPlot(plot);
		
	}
	
	public void setGetMovieIMDB(@RequestParam("consultar") String imdbID) {
		
		imdbID = tt;
		MovieOMDB movieOMDB = new MovieOMDB();
		movieOMDB = movieService.getRandomMovie(imdbID);
		
		titulo = movieOMDB.getTitle();
		year = movieOMDB.getYear();
		released = movieOMDB.getReleased();
		runtime = movieOMDB.getRuntime();
		genre = movieOMDB.getGenre();
		director = movieOMDB.getDirector();
		actors = movieOMDB.getActors();
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
		movieOMDB.setDirector(director);
		movieOMDB.setActors(actors);
		movieOMDB.setLanguage(language);
		movieOMDB.setCountry(country);
		movieOMDB.setPoster(poster);
		movieOMDB.setRated(rated);
		movieOMDB.setImdbRating(imdbRating);
		movieOMDB.setImdbID(imdbID);
		movieOMDB.setPlot(plot);
	}
	
	public void addAtribute(ModelMap model) {
		
		model.addAttribute("title", titulo);
		model.addAttribute("year", year);
		model.addAttribute("released", released);
		model.addAttribute("runtime", runtime);
		model.addAttribute("genre", genre);
		model.addAttribute("director", director);
		model.addAttribute("actors", actors);
		model.addAttribute("language", language);
		model.addAttribute("country", country);
		model.addAttribute("poster", poster);
		model.addAttribute("rated", rated);
		model.addAttribute("imdbRating", imdbRating);
		model.addAttribute("plot", plot);
		
	}

	@PostMapping(value = "/pesquisar")
	public String postMoviePesquisa(@RequestParam("consultar") String pegarTitulo) {

		setGetMovie(pegarTitulo);

		System.out.println(imdbID);

		return "redirect:/filmes";

	}

	@PostMapping(value = "/pesquisarLogado")
	public String postMoviePesquisaLogado(@RequestParam("consultar") String pegarTitulo) {
		
		setGetMovie(pegarTitulo);

		System.out.println(imdbID);

		return "redirect:/filmesLogado";

	}

	@GetMapping(value = "/filmesLogado")
	public String getMovieLogado(ModelMap modelMap, Principal principal, PessoaModel pessoaModel, Model model) {

		currentUserName(principal, pessoaModel, model);

		model.addAttribute("pessoaModel", pessoaModel);

		model.addAttribute("title", titulo);
		model.addAttribute("year", year);
		model.addAttribute("released", released);
		model.addAttribute("runtime", runtime);
		model.addAttribute("genre", genre);
		model.addAttribute("director", director);
		model.addAttribute("actors", actors);
		model.addAttribute("language", language);
		model.addAttribute("country", country);
		model.addAttribute("poster", poster);
		model.addAttribute("rated", rated);
		model.addAttribute("imdbRating", imdbRating);
		model.addAttribute("plot", plot);
		
		return "pesquisarLogado";

	}

	@GetMapping(value = "/filmes")
	public String getMovie(ModelMap model) {

		addAtribute(model);
		
		return "pesquisar";

	}

	long number = 0;
	String tt = "";

	@PostMapping(value = "/random/movie")
	public String randomMovie(@RequestParam("consultar") String imdbID) {
		number = (long) (1000000l + Math.random() * 8999999l);
		String tt = "tt" + number;

		imdbID = tt;
		movieService.getMovie(imdbID);
		MovieOMDB movieOMDB = movieService.getRandomMovie(imdbID);
		System.out.println(imdbID);

		ModelAndView andView = new ModelAndView();
		andView.addObject("consultar", movieOMDB);

		titulo = movieOMDB.getTitle();
		poster = movieOMDB.getPoster();
		imdbRating = movieOMDB.getImdbRating();

		if (titulo == null || poster.contains("N/A") || imdbRating.contains("N/A")) {

			titulo = "";
			poster = "N/A";
			imdbRating = "N/A";

			while (titulo.isEmpty() && poster.contains("N/A") && imdbRating.contains("N/A")) {
				number = (long) (1000000l + Math.random() * 8999999l);
				tt = "tt" + number;
				imdbID = tt;
				movieOMDB = movieService.getRandomMovie(imdbID);
				System.out.println(imdbID);

				if (movieOMDB.getTitle() != null && !movieOMDB.getImdbRating().contains("N/A")
						&& !movieOMDB.getPoster().contains("N/A")) {
					
					titulo = movieOMDB.getTitle();
					year = movieOMDB.getYear();
					released = movieOMDB.getReleased();
					runtime = movieOMDB.getRuntime();
					genre = movieOMDB.getGenre();
					director = movieOMDB.getDirector();
					actors = movieOMDB.getActors();
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
					movieOMDB.setDirector(director);
					movieOMDB.setActors(actors);
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
		}

		if (movieOMDB.getTitle() != null && !movieOMDB.getImdbRating().contains("N/A")
				&& !movieOMDB.getPoster().contains("N/A")) {
			titulo = movieOMDB.getTitle();
			year = movieOMDB.getYear();
			released = movieOMDB.getReleased();
			runtime = movieOMDB.getRuntime();
			genre = movieOMDB.getGenre();
			director = movieOMDB.getDirector();
			actors = movieOMDB.getActors();
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
			movieOMDB.setDirector(director);
			movieOMDB.setActors(actors);
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

	@GetMapping(value = "/random")
	public String getRandomMovie(ModelMap model) {
		
		addAtribute(model);
		
		return "random";
	}

	@SuppressWarnings("static-access")
	@GetMapping(value = "/assistidos")
	public ModelAndView getAssistido(ModelMap modelMap, Principal principal, PessoaModel pessoaModel, Model model) {
		currentUserName(principal, pessoaModel, model);
		@SuppressWarnings("unused")
		String nome = principal.getName();

		Long id;
		ModelAndView modelAndView = new ModelAndView("assistidos");

		ImplementacaoUserDetailsService iDetailsService = new ImplementacaoUserDetailsService();
		nome = iDetailsService.getNome();
		id = iDetailsService.getId();

		pessoaRepository.findById(id);

		modelAndView.addObject("pessoaobj", pessoaModel.getClass());
		modelAndView.addObject("assistido", assistidosRepository.getAssistidos(id));

		currentUserName(principal, pessoaModel, model);

		model.addAttribute("pessoaModel", pessoaModel);

		return modelAndView;
	}

	@SuppressWarnings("static-access")
	@PostMapping(value = "/assistidos")
	public ModelAndView setAssistido(ModelMap modelMap, Principal principal, PessoaModel pessoaModel, Model model) {
		currentUserName(principal, pessoaModel, model);
		getMovieService();
		@SuppressWarnings("unused")
		String nome = principal.getName();

		MovieOMDB movieOMDB = new MovieOMDB();
		Assistidos assistidos = new Assistidos();

		Long id;
		ModelAndView modelAndView = new ModelAndView("assistidos");

		ImplementacaoUserDetailsService iDetailsService = new ImplementacaoUserDetailsService();
		nome = iDetailsService.getNome();
		id = iDetailsService.getId();

		pessoaRepository.findById(id);

		while (imdbID != movieOMDB.getImdbID() && movieService != null) {

			movieOMDB = movieService.getOmdbID(imdbID);

			titulo = movieOMDB.getTitle();
			year = movieOMDB.getYear();
			released = movieOMDB.getReleased();
			runtime = movieOMDB.getRuntime();
			genre = movieOMDB.getGenre();
			director = movieOMDB.getDirector();
			actors = movieOMDB.getActors();
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
			movieOMDB.setDirector(director);
			movieOMDB.setActors(actors);
			movieOMDB.setLanguage(language);
			movieOMDB.setCountry(country);
			movieOMDB.setPoster(poster);
			movieOMDB.setRated(rated);
			movieOMDB.setImdbRating(imdbRating);
			movieOMDB.setImdbID(imdbID);
			movieOMDB.setPlot(plot);

			addAtribute(modelMap);

			pessoaModel.setId(id);

			assistidos.setPessoaModel(pessoaModel);
			assistidos.setGenre(genre);
			assistidos.setPoster(poster);
			assistidos.setTitulo(titulo);
			assistidos.setDirector(director);
			assistidos.setActors(actors);
			assistidos.setImdbID(imdbID);
			assistidos.setSinopse(plot);
			
			try {
				assistidosRepository.save(assistidos);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		currentUserName(principal, pessoaModel, model);

		modelAndView.addObject("pessoaobj", pessoaModel.getClass());
		modelAndView.addObject("assistido", assistidosRepository.getAssistidos(id));

		model.addAttribute("pessoaModel", pessoaModel);

		return modelAndView;
	}
	
	@GetMapping(value = "/removerAssistido/{assistidoid}")
	public ModelAndView deletarAssistido(@PathVariable("assistidoid") Long assistidoid) {
		PessoaModel pessoaModel = assistidosRepository.findById(assistidoid).get().getPessoaModel();

		assistidosRepository.deleteById(assistidoid);

		ModelAndView andView = new ModelAndView("redirect:/assistidos");
		andView.addObject("assistidos", assistidosRepository.getAssistidos(pessoaModel.getId()));
		return andView;
	}

	@SuppressWarnings("static-access")
	@GetMapping(value = "/listas")
	public ModelAndView getLista(ModelMap modelMap, Principal principal, PessoaModel pessoaModel, Model model) {
		currentUserName(principal, pessoaModel, model);
		@SuppressWarnings("unused")
		String nome = principal.getName();

		Long id;
		ModelAndView modelAndView = new ModelAndView("listas");

		ImplementacaoUserDetailsService iDetailsService = new ImplementacaoUserDetailsService();
		nome = iDetailsService.getNome();
		id = iDetailsService.getId();

		pessoaRepository.findById(id);

		modelAndView.addObject("pessoaobj", pessoaModel.getClass());
		modelAndView.addObject("lista", listaRepository.getLista(id));

		currentUserName(principal, pessoaModel, model);

		model.addAttribute("pessoaModel", pessoaModel);

		return modelAndView;
	}

	@SuppressWarnings("static-access")
	@PostMapping(value = "/listas")
	public ModelAndView setLista(ModelMap modelMap, Principal principal, PessoaModel pessoaModel, Model model) {
		currentUserName(principal, pessoaModel, model);
		getMovieService();
		@SuppressWarnings("unused")
		String nome = principal.getName();

		MovieOMDB movieOMDB = new MovieOMDB();
		Lista lista = new Lista();

		Long id;
		ModelAndView modelAndView = new ModelAndView("listas");

		ImplementacaoUserDetailsService iDetailsService = new ImplementacaoUserDetailsService();
		nome = iDetailsService.getNome();
		id = iDetailsService.getId();

		pessoaRepository.findById(id);

		while (imdbID != movieOMDB.getImdbID() && movieService != null) {

			movieOMDB = movieService.getOmdbID(imdbID);

			titulo = movieOMDB.getTitle();
			year = movieOMDB.getYear();
			released = movieOMDB.getReleased();
			runtime = movieOMDB.getRuntime();
			genre = movieOMDB.getGenre();
			director = movieOMDB.getDirector();
			actors = movieOMDB.getActors();
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
			movieOMDB.setDirector(director);
			movieOMDB.setActors(actors);
			movieOMDB.setLanguage(language);
			movieOMDB.setCountry(country);
			movieOMDB.setPoster(poster);
			movieOMDB.setRated(rated);
			movieOMDB.setImdbRating(imdbRating);
			movieOMDB.setImdbID(imdbID);
			movieOMDB.setPlot(plot);

			addAtribute(modelMap);

			pessoaModel.setId(id);

			lista.setPessoaModel(pessoaModel);
			lista.setGenre(genre);
			lista.setPoster(poster);
			lista.setTitulo(titulo);
			lista.setDirector(director);
			lista.setActors(actors);
			lista.setImdbID(imdbID);
			lista.setSinopse(plot);

			try {
			listaRepository.save(lista);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		currentUserName(principal, pessoaModel, model);

		modelAndView.addObject("pessoaobj", pessoaModel.getClass());
		modelAndView.addObject("lista", listaRepository.getLista(id));

		model.addAttribute("pessoaModel", pessoaModel);

		return modelAndView;
	}

	@GetMapping(value = "/removerLista/{listaid}")
	public ModelAndView deletarLista(@PathVariable("listaid") Long listaid) {

		listaRepository.deleteById(listaid);

		ModelAndView andView = new ModelAndView("redirect:/listas");
		andView.addObject("lista", listaRepository.getLista(listaid));

		return andView;
	}
	
	@GetMapping(value = "/genero/assistidos")
	public ModelAndView generoAssistidos(String acao, Principal principal, PessoaModel pessoaModel, Model model) {
		
		currentUserName(principal, pessoaModel, model);

		ModelAndView acaoView = new ModelAndView("assistidos");

		switch (acao) {

		case "acao": {

			acao = "Action";
			acaoView.addObject("assistido", assistidosRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "aventura": {

			acao = "Adventure";
			acaoView.addObject("assistido", assistidosRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "comedia": {

			acao = "Comedy";
			acaoView.addObject("assistido", assistidosRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "drama": {

			acao = "Drama";
			acaoView.addObject("assistido", assistidosRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "horror": {

			acao = "Horror";
			acaoView.addObject("assistido", assistidosRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "suspense": {

			acao = "Suspense";
			acaoView.addObject("assistido", assistidosRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "terror": {

			acao = "Terror";
			acaoView.addObject("assistido", assistidosRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "fantasia": {

			acao = "Fantasy";
			acaoView.addObject("assistido", assistidosRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + acaoView);
		}
		
	}
	
	@GetMapping(value = "/genero/listas")
	public ModelAndView generoListas(String acao, Principal principal, PessoaModel pessoaModel, Model model) {
		
		currentUserName(principal, pessoaModel, model);
		
		ModelAndView acaoView = new ModelAndView("listas");

		switch (acao) {

		case "acao": {

			acao = "Action";
			acaoView.addObject("lista", listaRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "aventura": {

			acao = "Adventure";
			acaoView.addObject("lista", listaRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "comedia": {

			acao = "Comedy";
			acaoView.addObject("lista", listaRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "drama": {

			acao = "Drama";
			acaoView.addObject("lista", listaRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "horror": {

			acao = "Horror";
			acaoView.addObject("lista", listaRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "suspense": {

			acao = "Suspense";
			acaoView.addObject("lista", listaRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "terror": {

			acao = "Terror";
			acaoView.addObject("lista", listaRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		case "fantasia": {

			acao = "Fantasy";
			acaoView.addObject("lista", listaRepository.getGenero(acao));
			acaoView.addObject("pessoaobj", pessoaModel.getClass());
			currentUserName(principal, pessoaModel, model);
			model.addAttribute("pessoaModel", pessoaModel);

			return acaoView;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + acaoView);
		}
		
	}

	@GetMapping(value = "/{id}")
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

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
		movieService.deleteMovie(id);
		return ResponseEntity.noContent().build();
	}

	private void addHateoas(MovieVO movieVO) {
		movieVO.add(linkTo(methodOn(MovieController.class).getById(movieVO.getId())).withSelfRel());
	}

	@GetMapping(value = "/minhaconta")
	public void currentUserName(Principal principal, PessoaModel pessoaModel, Model model) {
		PessoaController pessoaController = new PessoaController();
		pessoaController.currentUserName(principal, pessoaModel, model);

	}
}
