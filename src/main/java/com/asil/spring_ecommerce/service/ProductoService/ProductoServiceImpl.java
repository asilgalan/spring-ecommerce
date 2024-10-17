package com.asil.spring_ecommerce.service.ProductoService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asil.spring_ecommerce.models.Producto;
import com.asil.spring_ecommerce.repository.ProductoRepository;
@Service
public class ProductoServiceImpl implements IProductoService {


    @Autowired
    private ProductoRepository repository;

    @Override
    public List<Producto> findAll() {
        
       return repository.findAll();
        
    }

    @Override
    public Optional<Producto> findOne(Long id) {
        
        return repository.findById(id);
       
    }

    @Override
    public Producto save(Producto producto) {
        
        if(producto.getId()==null){
            return repository.save(producto);
        }else{
            return repository.save(producto);
        }
    }

    @Override
    public void delete(Long id) {
      
        repository.deleteById(id);
    }
    
}
