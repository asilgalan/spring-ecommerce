package com.asil.spring_ecommerce.service.UsuarioService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import com.asil.spring_ecommerce.models.Usuario;
import com.asil.spring_ecommerce.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Service

public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UserDetailsService services;
    @Autowired
    private SecurityContextRepository securityContextRepository;
     
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public List<Usuario> findAll() {
    
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> findOne(Long id) {
      
        return repository.findById(id);
    }

    @Override
    public void save(Usuario usuario,HttpServletRequest request,HttpServletResponse response) {
      
      repository.save(usuario);

      UserDetails user=services.loadUserByUsername(usuario.getEmail());
      Authentication authentication=authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword(),user.getAuthorities())
      );
      SecurityContextHolderStrategy securityContextHolderStrategy=SecurityContextHolder.getContextHolderStrategy();

      SecurityContext context=SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authentication);
      securityContextHolderStrategy.setContext(context);
      securityContextRepository.saveContext(context, request, response);
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
