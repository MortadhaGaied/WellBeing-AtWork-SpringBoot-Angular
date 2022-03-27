package com.wellbeignatwork.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the user database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
public class User implements Serializable {

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String name ;

	@Enumerated(EnumType.STRING)
	private ERole role;

	@OneToMany(mappedBy = "userRes")
	Set<Reservation> reservations;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@ManyToMany(mappedBy = "employee", fetch = FetchType.EAGER
			,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
	@JsonIgnore
	private Set<Formation> formationE;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy ="User")
	@JsonIgnore
	private Session session;
	
	@Column(name = "DISPLAY_NAME")
	private String displayName;
}