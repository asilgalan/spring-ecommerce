package com.asil.spring_ecommerce.service.DetalleOrden;

import java.util.List;
import java.util.Optional;

import com.asil.spring_ecommerce.models.DetalleOrden;


public interface  IDetalleOrdenService {
     List<DetalleOrden> findAll();
    Optional<DetalleOrden> findOne(Long id);
    DetalleOrden save(DetalleOrden detalleOrden);
    void delete(Long id);
    
}
