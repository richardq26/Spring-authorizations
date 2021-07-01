package com.restapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
	@Data
	@Entity
	@Table(name = "rol")
	public class RolModel {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(unique = true, nullable = false)
		private Long id;
		private String nombre;
	}

