package com.movie.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.movie.model.PessoaModel;
import com.movie.repository.PessoaRepository;

@Service
@Transactional
public class ImplementacaoUserDetailsService implements UserDetailsService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		PessoaModel pessoaModel = pessoaRepository.findUserByLogin(username);
		try {

			if (pessoaModel == null) {
				throw new UsernameNotFoundException("Usuário não foi encontrado");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new User(pessoaModel.getUsername(), pessoaModel.getPassword(), pessoaModel.isEnabled(), true, true, true,
				pessoaModel.getAuthorities());
	}

	public void inserirAcessoPadrao(Long id) {

		// Descobre qual a constraint de restrição
		String constraint = pessoaRepository.consultaConstraintRole();

		if (constraint != null) {
			// Remove a constraint
			jdbcTemplate.execute("alter table pessoas_role drop constraint " + constraint);

		}
		// Insere os acessos padrão
		pessoaRepository.insereAcessoRolePadrao(id);

	}

}
