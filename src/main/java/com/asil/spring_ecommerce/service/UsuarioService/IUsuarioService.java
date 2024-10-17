package com.asil.spring_ecommerce.service.UsuarioService;

import java.util.List;
import java.util.Optional;

import com.asil.spring_ecommerce.models.Usuario;

public interface IUsuarioService {
    
    List<Usuario> findAll();
    Optional<Usuario> findOne(Long id);
    Usuario save(Usuario usuario);
    void delete(Long id);
    Optional<Usuario> findByEmail(String email);
}
