package com.cinema.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cinema.entities.Salle;
@RepositoryRestResource
@CrossOrigin("*")
public interface SalleRepository extends JpaRepository<Salle, Long> {
	public Page<Salle> findByNameContains(String mc,Pageable pageable);

	@Query(value = "SELECT * FROM salle WHERE cinema_id = :idc" ,nativeQuery = true)
    public Page<Salle> findByIdCinema(@Param("idc") Long id, Pageable pageable);


}
