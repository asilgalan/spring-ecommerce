package com.asil.spring_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asil.spring_ecommerce.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
