package com.cinema.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cinema.entities.Cinema;
@RepositoryRestResource
@CrossOrigin("*")
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
	public Page<Cinema> findByNameContains(String mc,Pageable pageable);
	@Query(value = "SELECT * FROM cinema WHERE ville_id = :idv" ,nativeQuery = true)
    public List<Cinema> findByIdVille(@Param("idv") Long id);

}
