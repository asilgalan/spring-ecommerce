package com.asil.spring_ecommerce.service.UsuarioService;

import java.util.List;
import java.util.Optional;

import com.asil.spring_ecommerce.models.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IUsuarioService {
    
    List<Usuario> findAll();
    Optional<Usuario> findOne(Long id);
    void save(Usuario usuario,HttpServletRequest request,HttpServletResponse response);
    void delete(Long id);
    Optional<Usuario> findByEmail(String email);
}
