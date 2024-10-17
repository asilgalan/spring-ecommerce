package com.asil.spring_ecommerce.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asil.spring_ecommerce.models.Producto;
import com.asil.spring_ecommerce.models.Usuario;
import com.asil.spring_ecommerce.service.ImagenService.UploadFileService;
import com.asil.spring_ecommerce.service.ProductoService.IProductoService;
import com.asil.spring_ecommerce.service.UsuarioService.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/productos")
public class ProductoControlle {

    @Autowired
    private IProductoService service;

    @Autowired 
    IUsuarioService UserService;
    @Autowired
    private UploadFileService upload;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos", service.findAll());

        return "productos/show";
    }

    @GetMapping("/create")
    public String create(Producto producto, Model model) {
        model.addAttribute("productos", producto);

        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("ima") MultipartFile file, Model model,HttpSession session) throws IOException {
        model.addAttribute("titulo", "Crear Producto");
        Usuario usuario = new Usuario();
          
        usuario=UserService.findOne(Long.parseLong(session.getAttribute("idusuario").toString())).get();
        producto.setUsuario(usuario);
        //imagen
        if (producto.getId() == null) {//cuando se crea un producto

            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);

        } else {
            if (file.isEmpty()) {//editamos el producto pero no cambiamos las imagenes
                Producto p = new Producto();
                p = service.findOne(producto.getId()).get();
                producto.setImagen(p.getImagen());
            } else {//cuando editamos la imagen
                String nombreImagen = upload.saveImage(file);
                producto.setImagen(nombreImagen);
            }
        }

        service.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        Optional<Producto> producto = service.findOne(id);
        model.addAttribute("productos", producto);
        model.addAttribute("titulo", "Editar Producto");

        return "productos/create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

      Producto p = new Producto();
      p = service.findOne(id).get();
      //eliminar cuando no sea la imagen por defecto
      if(!p.getImagen().equals("default.jpg")){

        upload.delete(p.getImagen());

      }

        service.delete(id);
        return "redirect:/productos";
    }

}
