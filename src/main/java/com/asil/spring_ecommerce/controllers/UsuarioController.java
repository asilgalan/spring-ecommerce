package com.asil.spring_ecommerce.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asil.spring_ecommerce.models.DetalleOrden;
import com.asil.spring_ecommerce.models.Orden;
import com.asil.spring_ecommerce.models.Usuario;
import com.asil.spring_ecommerce.service.OrdenService.IOrdenService;
import com.asil.spring_ecommerce.service.UsuarioService.IUsuarioService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenservice;

  

    @Autowired
    private PasswordEncoder passwordEncoder;




  

    @GetMapping("/form/registro")
    public String form() {
        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario, BindingResult result) {

        if (result.hasErrors()) {
            return "usuario/registro";
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setTipo("USER");

        usuarioService.save(usuario);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {

        return "usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session) {
        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
        if (user.isPresent()) {
            session.setAttribute("idusuario", user.get().getId());
            if (user.get().getTipo().equals("ADMIN")) {
                return "redirect:/administrador";
            } else {
                return "redirect:/";
            }
        } else {
            session.setAttribute("error", "Usuario o Contraseña incorrecto");
            return "usuario/login";
        }

    }

    @GetMapping("/salir")
    public String salir(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/compras")
    public String compras(HttpSession session, Model model) {
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        Usuario usuario=usuarioService.findOne(Long.parseLong(session.getAttribute("idusuario").toString())).get();
        List<Orden> ordenes=ordenservice.findByUsuario(usuario);
        model.addAttribute("ordenes",ordenes);
        return "usuario/compras";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id,Model model,HttpSession session) {
        Optional<Orden> orden=ordenservice.findOne(id);
        model.addAttribute("sesion", session.getAttribute("idusuario"));
        if (!orden.isPresent()) {
            return "redirect:/usuario/compras"; // Redirigir si no se encuentra la orden
        }
         List<DetalleOrden> detalles = orden.get().getDetallesOrden();
         System.out.println(detalles);
        model.addAttribute("detalles",detalles);
        
        return "usuario/detallecompra";
    }
    
}
