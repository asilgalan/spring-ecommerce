package com.asil.spring_ecommerce.service.OrdenService;

import java.util.List;
import java.util.Optional;

import com.asil.spring_ecommerce.models.Orden;
import com.asil.spring_ecommerce.models.Usuario;

public interface  IOrdenService  {
    
    List<Orden> findAll();
    Optional<Orden> findOne(Long id);
    Orden save(Orden usuario);
    void delete(Long id);
    String generarNumeroOrden();
    List<Orden> findByUsuario(Usuario usuario);
}
