package com.asil.spring_ecommerce.service.ProductoService;

import java.util.List;
import java.util.Optional;

import com.asil.spring_ecommerce.models.Producto;

public interface IProductoService {

    public List<Producto> findAll();
    public Optional<Producto> findOne(Long id);
    public Producto save(Producto producto);
    public void delete(Long id);

    
    
}
