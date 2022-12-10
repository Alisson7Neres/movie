package com.movie.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.movie.model.Assistidos;
import com.movie.model.PessoaModel;
import com.movie.model.Role;
import com.movie.repository.AssistidosRepository;
import com.movie.repository.ListaRepository;
import com.movie.repository.PessoaRepository;
import com.movie.security.ImplementacaoUserDetailsService;
import com.movie.service.MovieService;
import com.movie.service.PessoaService;
import com.movie.service.RoleService;

@Controller
@RequestMapping(value = "conta")
public class PessoaController {

	@Autowired
	PessoaService pessoaService;

	@Autowired
	MovieService movieService;

	@Autowired
	RoleService roleService;

	public MovieService getMovieService() {
		return movieService;
	}

	@Autowired
	private AssistidosRepository assistidosRepository;

	@Autowired
	private ListaRepository listaRepository;

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	@Autowired
	private PessoaRepository pessoaRepository;

	@PostMapping(value = "/create")
	public String setPessoa(PessoaModel pessoaModel) {

		Role role = new Role();

		String senhaCriptografada = new BCryptPasswordEncoder().encode(pessoaModel.getSenha());
		pessoaModel.setSenha(senhaCriptografada);

		pessoaModel = pessoaService.setPessoa(pessoaModel);

		if (role.getNomeRole() == null && role.getId() == null) {
			role.setNomeRole("ROLE_USER");
			long id = pessoaModel.getId();
			role.setId(id);
			roleService.save(role);
		}

		implementacaoUserDetailsService.inserirAcessoPadrao(pessoaModel.getId());
		return "login";

	}

	@PostMapping(value = "/pesquisar")
	public void postMoviePesquisa(@RequestParam("consultar") String pegarTitulo) {
		MovieController movieController = new MovieController();
		movieController.getMovieService();
		movieController.postMoviePesquisa(pegarTitulo);
	}

	@PostMapping(value = "/random")
	public void randomMovie(@RequestParam("consultar") String imdbID) {
		MovieController movieController = new MovieController();
		movieController.getMovieService();
		movieController.randomMovie(imdbID);
	}

	@GetMapping(value = "/minhaconta")
	public String currentUserName(Principal principal, PessoaModel pessoaModel, Model model) {
		Long id = 0L;
		String login = principal.getName();
		String nome = "";

		ImplementacaoUserDetailsService iDetailsService = new ImplementacaoUserDetailsService();
		nome = iDetailsService.getNome();

		pessoaModel.setId(id);
		pessoaModel.setEmail(login);
		pessoaModel.setNome(nome);
		model.addAttribute("pessoaModel", pessoaModel);

		return "minhaconta";
	}

	@GetMapping(value = "/assistidos")
	public ModelAndView assistidos(Principal principal, PessoaModel pessoaModel, Model model) {
		currentUserName(principal, pessoaModel, model);
		String nome = "";
		nome = principal.getName();

		Assistidos assistidos = new Assistidos();

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

	@GetMapping(value = "/listas")
	public ModelAndView lista(Principal principal, PessoaModel pessoaModel, Model model) {
		currentUserName(principal, pessoaModel, model);
		String nome = "";
		nome = principal.getName();

		Assistidos assistidos = new Assistidos();

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

	@GetMapping(value = "/genero")
	public ModelAndView genero(String acao, Principal principal, PessoaModel pessoaModel) {
		ModelAndView andView = new ModelAndView("minhaconta");
		String nome = principal.getName();

		Long id;
		ModelAndView modelAndView = new ModelAndView("assistidos");

		ImplementacaoUserDetailsService iDetailsService = new ImplementacaoUserDetailsService();
		nome = iDetailsService.getNome();
		id = iDetailsService.getId();

		pessoaRepository.findById(id);

		modelAndView.addObject("pessoaobj", pessoaModel.getClass());
		modelAndView.addObject("assistido", assistidosRepository.getAssistidos(id));
		if (acao.equalsIgnoreCase("acao")) {
			acao = "Action";
			andView.addObject("assistido", assistidosRepository.getGenero(acao));
			ModelAndView acaoView = new ModelAndView("assistidos");
			return acaoView;
		}
		if (acao.equalsIgnoreCase("aventura")) {
			System.out.println("aventura");
		}
		if (acao.equalsIgnoreCase("comedia")) {
			System.out.println("comedia");
		}
		if (acao.equalsIgnoreCase("drama")) {
			System.out.println("drama");
		}
		if (acao.equalsIgnoreCase("suspense")) {
			System.out.println("suspense");
		}
		if (acao.equalsIgnoreCase("terror")) {
			System.out.println("terror");
		}
		if (acao.equalsIgnoreCase("fantasia")) {
			System.out.println("fantasia");
		}
		return andView;
	}

	@GetMapping(value = "/login")
	public String getLogin() {
		return "login";
	}

	@GetMapping(value = "/logado")
	public String logado() {
		return "minhaconta";
	}

}
