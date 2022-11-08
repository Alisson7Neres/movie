package com.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movie.model.PessoaModel;
import com.movie.security.ImplementacaoUserDetailsService;
import com.movie.service.PessoaService;

@Controller
@RequestMapping(value = "conta")
public class PessoaController {
	
	@Autowired
	PessoaService pessoaService;
	
	@Autowired
	ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	@PostMapping(value = "/create")
	public String setPessoa (PessoaModel pessoaModel) {
		
		String senhaCriptografada = new BCryptPasswordEncoder().encode(pessoaModel.getSenha());
		pessoaModel.setSenha(senhaCriptografada);
		
		pessoaModel = pessoaService.setPessoa(pessoaModel);
		
		implementacaoUserDetailsService.inserirAcessoPadrao(pessoaModel.getId());
		return "login";
		
	}
	
	@GetMapping(value = "/login")
	public String getLogin() {
		return "login";
	}
	
	@GetMapping(value = "/logado")
	public String logado() {
		return "logado";
	}

}
