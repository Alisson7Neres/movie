package com.movie.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "pessoa")
public class PessoaModel implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "email")
	private String email;

	@Column(name = "senha")
	private String senha;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "pessoas_role", uniqueConstraints = @UniqueConstraint(columnNames = { "pessoa_id",
	"role_id" }, name = "unique_role_user"), 
	joinColumns = @JoinColumn(name = "pessoa_id", 
	referencedColumnName = "id", table = "pessoa", unique = false, foreignKey = 
	@ForeignKey(name = "pessoa_fk", value = ConstraintMode.CONSTRAINT)), inverseJoinColumns = 
	@JoinColumn(name = "role_id", referencedColumnName = "id", table = "role", unique = false, updatable = false, foreignKey =
	@ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)))
	private List<Role> roles = new ArrayList<Role>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public Collection<Role> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
