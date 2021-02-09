package com.cinema.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cinema.entities.Film;
@RepositoryRestResource
@CrossOrigin("*")
public interface FilmRepository extends JpaRepository<Film, Long> {
	public Page<Film> findByTitreContains(String mc,Pageable pageable);


}
