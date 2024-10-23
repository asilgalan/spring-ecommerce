package com.asil.spring_ecommerce.service.UsuarioService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.asil.spring_ecommerce.models.Usuario;

import jakarta.servlet.http.HttpSession;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String gmail) throws UsernameNotFoundException {
        Optional<Usuario> usuariosOptional = usuarioService.findByEmail(gmail);
        if (usuariosOptional.isPresent()) {
            session.setAttribute("idusuario", usuariosOptional.get().getId());
            Usuario usuario = usuariosOptional.get();
            return User.builder()
                .username(usuario.getNombre()) // Asegúrate de usar el email o nombre de usuario correcto
                .password(usuario.getPassword()) // No vuelvas a codificar la contraseña aquí
                .roles(usuario.getTipo()) // Asegúrate de que 'usuario.getTipo()' retorne el rol correcto
                .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}

