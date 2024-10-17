package com.asil.spring_ecommerce;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//configuracion para cargar las imagenes en el usuario
@Configuration
public class ResourceWebConfiguracion implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      
        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
        
    }
    
}
