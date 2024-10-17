package com.asil.spring_ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="detalles_ordenes")
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 
    private String nombre;
    private double cantidad;
    
    private double precio;
   
    private double total;

    @ManyToOne
    private Orden orden;

    @ManyToOne(fetch=FetchType.LAZY)
    private Producto productos;
    
    public DetalleOrden() {
       
       
    }

    public DetalleOrden(double cantidad, Long id, String nombre, Orden orden, double precio, double total) {
        this.cantidad = cantidad;
        this.id = id;
        this.nombre = nombre;
        this.orden = orden;
        this.precio = precio;
  
        this.total = total;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

 public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DetalleOrden{");
        sb.append("id=").append(id);
        sb.append(", cantidad=").append(cantidad);
        sb.append(", precio=").append(precio);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }

    public Producto getProductos() {
        return productos;
    }

    public void setProductos(Producto productos) {
        this.productos = productos;
    }

    
}
