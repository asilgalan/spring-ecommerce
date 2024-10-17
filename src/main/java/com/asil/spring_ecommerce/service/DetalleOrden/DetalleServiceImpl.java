package com.asil.spring_ecommerce.service.DetalleOrden;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asil.spring_ecommerce.models.DetalleOrden;
import com.asil.spring_ecommerce.repository.DetalleOrdenRepository;
@Service
public class DetalleServiceImpl implements IDetalleOrdenService {
 @Autowired
 private DetalleOrdenRepository  detalleOrdenRepository;
    @Override
    public List<DetalleOrden> findAll() {
       
        return detalleOrdenRepository.findAll();
    }

    @Override
    public Optional<DetalleOrden> findOne(Long id) {
      
        return detalleOrdenRepository.findById(id);
    }

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
      if(detalleOrden.getId() != null && detalleOrden.getId() >0){
        return detalleOrdenRepository.save(detalleOrden);
      }else{
        return detalleOrdenRepository.save(detalleOrden);
      }
    }

    @Override
    public void delete(Long id) {

       detalleOrdenRepository.deleteById(id);
       
    }
    
}
