package com.restapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Data;

//Data de lombok genera los getters y setters

@Data
@Entity
@Table(name = "usuario")
public class UsuarioModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	@Size(min=4, max=12)
	private String nombre;

	@Column(unique = true, nullable = false, length=20)
	@Email
	private String email;

	private int prioridad;
	private String password;
	private String role;
}
