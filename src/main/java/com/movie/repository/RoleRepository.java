package com.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
