package com.asil.spring_ecommerce.controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asil.spring_ecommerce.models.DetalleOrden;
import com.asil.spring_ecommerce.models.Orden;
import com.asil.spring_ecommerce.models.Producto;
import com.asil.spring_ecommerce.models.Usuario;
import com.asil.spring_ecommerce.service.DetalleOrden.IDetalleOrdenService;
import com.asil.spring_ecommerce.service.OrdenService.IOrdenService;
import com.asil.spring_ecommerce.service.ProductoService.IProductoService;
import com.asil.spring_ecommerce.service.UsuarioService.IUsuarioService;

import jakarta.servlet.http.HttpSession;





@Controller
@RequestMapping("/")
public class HomeController {
  
    @Autowired
    private IProductoService service;
  
     @Autowired
     private IUsuarioService usuarioService;

     @Autowired
     private IOrdenService ordenService;

     @Autowired
     private IDetalleOrdenService detalleOrdenService;

    List<DetalleOrden> detalles=new ArrayList<DetalleOrden>();

    Orden orden=new Orden();

    //mostrar los articulos
    @GetMapping("")
    public String Home(Model model,HttpSession session){


        model.addAttribute("productos",service.findAll());
        model.addAttribute("sesion",session.getAttribute("idusuario"));

        return "usuario/homeUsuario";
    }

    //ver producto completo
    @GetMapping("/productohome/{id}")
    public String productoHome(@PathVariable Long id,Model model){

        Producto producto=new Producto();
        Optional<Producto> productoOptional=service.findOne(id);
        producto=productoOptional.get();

        model.addAttribute("producto", producto);
        
        return "usuario/productohome";

    }

    //redireccionar al carrito y mostrar los productos añadidos
     @PostMapping("/cart")
     public String addCart(@RequestParam Long id, @RequestParam Integer cantidad,Model model) {

        DetalleOrden detalleOrden=new DetalleOrden();
        Producto   p=new Producto();
        double sumaTotal=0;
      
        Optional<Producto> optionalproducto=service.findOne(id);

        p=optionalproducto.get();
           
        //para saber si el producto esta
       Optional<DetalleOrden> existe = detalles.stream()
        .filter(detalle ->  detalle.getProductos().getId().equals(id))
        .findFirst();

    if (existe.isPresent()) {
         //actualizo el carrito con la nueva cantidad
        DetalleOrden siexiste = existe.get();
        siexiste.setCantidad(siexiste.getCantidad() + cantidad); 
        //si cantidad es mayor  a 5 pongo cantidad a 5
        if(siexiste.getCantidad()>5){
            siexiste.setCantidad(5);
        }
        //actualizo el nuevo precio
        siexiste.setTotal(siexiste.getPrecio() * siexiste.getCantidad()); 
    } else {
        //si el producto no lo tengo en el carrito simplemente lo añado
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(p.getPrecio());
        detalleOrden.setNombre(p.getNombre());
        detalleOrden.setTotal(p.getPrecio() * cantidad);
        detalleOrden.setProductos(p);
       
        //lo añade al
        detalles.add(detalleOrden);
    }

        

        sumaTotal=detalles.stream().mapToDouble(t-> t.getTotal()).sum();
         orden.setTotal(sumaTotal);
         model.addAttribute("cart", detalles);
         model.addAttribute("orden",orden);
        return "usuario/carrito";
    }

    //eliminar producto del carrito

    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Long id,Model model) {
       
        //lista nueva de producto si he eliminado algun producto
        detalles.removeIf(detalle ->  detalle.getProductos().getId().equals(id));
          
             
        double sumaTotal=0;
        sumaTotal=detalles.stream().mapToDouble(t-> t.getTotal()).sum();
        orden.setTotal(sumaTotal);
         model.addAttribute("cart", detalles);
         model.addAttribute("orden",orden);
        return"usuario/carrito";
    }
    
    //boton carrito
    @GetMapping("/carrito")
    public String cart(Model model,HttpSession session){
        model.addAttribute("cart", detalles);
        model.addAttribute("orden",orden);
        //session
        model.addAttribute("sesion",session.getAttribute("idusuario"));
        return "usuario/carrito";
    }

    //me muestra la orden
    @GetMapping("/order")
    public String order(Model model,HttpSession session){

        Usuario usuario=usuarioService.findOne(Long.parseLong(session.getAttribute("idusuario").toString())).get();
        model.addAttribute("cart", detalles);
        model.addAttribute("orden",orden);
        model.addAttribute("usuarios", usuario);
        return "usuario/resumenorden";

    }
          
    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session) {
       
        Date fechaCreacion=new Date();
        orden.setFechaCreacion( fechaCreacion);
        orden.setFechaRecibida(fechaCreacion);
        Usuario usuario=usuarioService.findOne(Long.parseLong(session.getAttribute("idusuario").toString())).get();
        orden.setNumero(ordenService.generarNumeroOrden());
        orden.setUsuario(usuario);
        ordenService.save(orden);

        for(DetalleOrden dt:detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }
      
        //LIMPIAR LISTA 
        orden=new Orden();
        detalles.clear();
        return "redirect:/";
    }

    //
    @PostMapping("/buscar")
    public String buscar(@RequestParam String nombre, Model model) {

   List<Producto> producto=service.findAll().stream().filter(p -> p.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList());
 model.addAttribute("productos",producto);
       
        
        return "usuario/homeUsuario";
    }
    
    
    
}
