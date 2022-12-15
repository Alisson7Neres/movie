package com.movie.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.movie.model.PessoaModel;
import com.movie.model.Role;
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
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	@PostMapping(value = "/create")
	public String setPessoa(PessoaModel pessoaModel,
			RedirectAttributes redirectAttributes) {
		
		try {
			
			pessoaModel = pessoaService.setPessoa(pessoaModel);
			
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "Email já cadastrado");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			return "redirect:/cadastrar";
		}

		Role role = new Role();

		String senhaCriptografada = new BCryptPasswordEncoder().encode(pessoaModel.getSenha());
		pessoaModel.setSenha(senhaCriptografada);

		if (role.getNomeRole() == null && role.getId() == null) {
			role.setNomeRole("ROLE_USER");
			long id = pessoaModel.getId();
			role.setId(id);
			roleService.save(role);
		}

		implementacaoUserDetailsService.inserirAcessoPadrao(pessoaModel.getId());
		return "login";

	}
	
	@GetMapping("/cadastrar")
	public String cadastrar(Model model){
	    return "cadastrar";
	}

	@SuppressWarnings("static-access")
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

	@GetMapping(value = "/login")
	public String getLogin() {
		return "login";
	}

}
