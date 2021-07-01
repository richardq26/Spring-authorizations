package com.restapi.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.restapi.models.JwtRequest;
import com.restapi.models.JwtResponse;
import com.restapi.models.UsuarioModel;
import com.restapi.security.JwtUtil;
import com.restapi.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	UsuarioService usuarioService;

	@GetMapping()
	public ArrayList<UsuarioModel> obtenerUsuarios() {
		return usuarioService.obtenerUsuarios();
	}

	// El actualizar se hace mandando el mismo id
	@PostMapping()
	public UsuarioModel guardarUsuario(@RequestBody UsuarioModel usuario) {
		return this.usuarioService.guardarUsuario(usuario);
	}

	@GetMapping(path = "/{id}")
	public Optional<UsuarioModel> obtenerUsuarioPorId(@PathVariable("id") Long id) {
		return this.usuarioService.obtenerPorId(id);
	}

	@GetMapping("/query")
	public ArrayList<UsuarioModel> obtenerUsuariosPorPrioridad(@RequestParam("prioridad") Integer prioridad) {
		return this.usuarioService.obtenerPorPrioridad(prioridad);
	}

	@DeleteMapping(path = "/{id}")
	public String eliminarPorId(@PathVariable("id") long id) {
		boolean ok = this.usuarioService.eliminarUsuario(id);
		if (ok) {
			return "Se eliminó al usuario con id " + id;
		} else {
			return "No se pudo eliminar al usuario con id " + id;
		}
	}

	@PostMapping("/login")
	public String login(@RequestBody UsuarioModel us) {
		return this.usuarioService.login(us.getEmail(), us.getPassword());
	}

	@GetMapping("/home")
	public String home() {
		return "HOLAAAAAAAAA";
	}

	//////////////////////////////////////////////////////
	///////////////// Para el JWT/////////////////////////
	//////////////////////////////////////////////////////
	
	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	public ResponseEntity<?> create(@RequestBody JwtRequest req) throws Exception {
		authenticate(req.getEmail(), req.getPassword());
		final UserDetails userDetails = usuarioService.loadUserByUsername(req.getEmail());
		
		/*
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			System.out.println("Usuario authenticado");
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid credentials", e);
		} 
		final UserDetails userDetails = usuarioService.loadUserByUsername(email);
		*/
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));

	}
	
	private void authenticate(String email, String password) throws Exception{
		try {
			System.out.println("la password" + password);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
