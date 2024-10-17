package com.asil.spring_ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asil.spring_ecommerce.models.Orden;
import com.asil.spring_ecommerce.models.Usuario;



@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByUsuario(Usuario usuario);
}
