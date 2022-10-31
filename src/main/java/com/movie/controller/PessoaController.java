package com.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movie.model.PessoaModel;
import com.movie.service.PessoaService;

@Controller
@RequestMapping(value = "conta")
public class PessoaController {
	
	@Autowired
	PessoaService pessoaService;
	
	@PostMapping(value = "/create")
	public ResponseEntity<PessoaModel> setPessoa(  PessoaModel pessoaModel) {
		pessoaModel = pessoaService.setPessoa(pessoaModel);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
