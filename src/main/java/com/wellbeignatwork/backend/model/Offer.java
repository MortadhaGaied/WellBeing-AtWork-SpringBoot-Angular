package com.wellbeignatwork.backend.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

public class Offer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long  idOffer;
	String title;
	String descrption;
	Date starDateOf;
	Date endDateOf;
	int nplaces;
	float promotion;
	int percentage;
	String localisation;
	float prix;

	
	@Enumerated(EnumType.STRING)
	Happy happy;
	
	@ManyToOne
	Collaboration collaboration;

	@OneToMany(mappedBy="offers", cascade=CascadeType.ALL)
	private Set<Publicity>  publicity;



	




}
