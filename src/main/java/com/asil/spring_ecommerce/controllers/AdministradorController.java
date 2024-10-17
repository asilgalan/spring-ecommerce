package com.asil.spring_ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.asil.spring_ecommerce.models.Usuario;
import com.asil.spring_ecommerce.service.ProductoService.IProductoService;
import com.asil.spring_ecommerce.service.UsuarioService.IUsuarioService;




@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    
    @Autowired
    private IProductoService service;

    @Autowired
    private IUsuarioService serviceUsuario;

     @GetMapping()
    public String home(Model model){
        
        model.addAttribute("productos",service.findAll());
        return "administrador/home";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model){

        List<Usuario> usuarios=serviceUsuario.findAll();
        
         model.addAttribute("usuarios",usuarios);
        return "administrador/usuarios";

    }
    
}
