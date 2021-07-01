package com.restapi.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restapi.models.UsuarioModel;
import com.restapi.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;
 
    public ArrayList<UsuarioModel> obtenerUsuarios() {
        return (ArrayList<UsuarioModel>) usuarioRepository.findAll();
    }

    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
    	PasswordEncoder passwordEncoder;
    	passwordEncoder= new BCryptPasswordEncoder();
    	String encodedPassword=passwordEncoder.encode(usuario.getPassword());
    	usuario.setPassword(encodedPassword);
        return usuarioRepository.save(usuario);
    }

    // Optional porque puede ser que no exista el id
    public Optional<UsuarioModel> obtenerPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public ArrayList<UsuarioModel> obtenerPorPrioridad(Integer prioridad){
        return usuarioRepository.findByPrioridad(prioridad);
    }

    public boolean eliminarUsuario(Long id){
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String login(String email, String password) {
    	UsuarioModel us = usuarioRepository.findByEmail(email);
    	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();  
    	
    	if(us!=null) {
    		boolean match = bcrypt.matches(password, us.getPassword());
    		if(match) {
    			return "Si existe";
    		}else {
    			return "Password incorrecta";
    		}
    		
    	}else {
    		return "No existe";
    	}
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
    	UsuarioModel us= usuarioRepository.findByEmail(email);
    	return new User(us.getEmail(), us.getPassword(), new ArrayList<>());
    }
}