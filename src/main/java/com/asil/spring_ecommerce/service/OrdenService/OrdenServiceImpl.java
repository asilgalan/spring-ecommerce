package com.asil.spring_ecommerce.service.OrdenService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asil.spring_ecommerce.models.Orden;
import com.asil.spring_ecommerce.models.Usuario;
import com.asil.spring_ecommerce.repository.OrdenRepository;
@Service
public class OrdenServiceImpl implements IOrdenService {

    @Autowired
    private OrdenRepository repository;

    @Override
    public List<Orden> findAll() {
        
       return repository.findAll();
        
    }

    @Override
    public Optional<Orden> findOne(Long id) {
        
        return repository.findById(id);
       
    }

    @Override
    public Orden save(Orden orden) {
        
        if(orden.getId()==null){
            return repository.save(orden);
        }else{
            return repository.save(orden);
        }
    }

    @Override
    public void delete(Long id) {
      
        repository.deleteById(id);
    }
    //numero de orden;
  // número de orden
public String generarNumeroOrden() {
    List<Orden> ordenes = findAll();
    int numero = ordenes.isEmpty() ? 1 : ordenes.stream()
                                                .mapToInt(o -> Integer.parseInt(o.getNumero()))
                                                .max()
                                                .orElse(0) + 1;
    return String.format("%08d", numero);
}

@Override
public List<Orden> findByUsuario(Usuario usuario) {

   return repository.findByUsuario(usuario);
}
}
