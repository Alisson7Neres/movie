package com.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.model.PessoaModel;
import com.movie.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	PessoaRepository pessoaRepository;

	public PessoaModel setPessoa(PessoaModel pessoaModel) {
		pessoaModel.setId(null);
		return pessoaRepository.save(pessoaModel);
	}
}
