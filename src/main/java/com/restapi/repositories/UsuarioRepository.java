package com.restapi.repositories;

import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.restapi.models.UsuarioModel;


@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long> {
    // Solo con el nombre se implementa, no es necesario un método
    public abstract ArrayList<UsuarioModel> findByPrioridad(Integer prioridad);
    public abstract UsuarioModel findByEmail(String email);
    
    
}