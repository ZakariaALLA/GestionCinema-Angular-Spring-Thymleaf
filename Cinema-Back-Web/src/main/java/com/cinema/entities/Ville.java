package com.cinema.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @NoArgsConstructor @AllArgsConstructor
public class Ville {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)

	private Long id;
	@NotNull
	@Size(min = 2,max = 30)
	private String name;
	private double longitude,latitude,altitude;
	@OneToMany(mappedBy = "ville")
	private Collection<Cinema> cinemas;

}
