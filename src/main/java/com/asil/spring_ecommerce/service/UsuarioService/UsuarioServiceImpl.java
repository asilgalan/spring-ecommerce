package com.asil.spring_ecommerce.service.UsuarioService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asil.spring_ecommerce.models.Usuario;
import com.asil.spring_ecommerce.repository.UsuarioRepository;
@Service
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> findAll() {
    
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> findOne(Long id) {
      
        return repository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
      
        if(usuario.getId() !=null && usuario.getId() >0){
            return repository.save(usuario);
        }else{
           return  repository.save(usuario);
        }
    }

    @Override
    public void delete(Long id) {
       repository.deleteById(id);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
      return repository.findByEmail(email);
    }

}
